package soft.appointment.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import soft.appointment.domain.Appointment;
import soft.appointment.domain.User;
import soft.appointment.persistence.AppointmentStorage;
import soft.appointment.persistence.UserStorage;
import soft.appointment.presentation.UserGui;
import soft.appointment.strategy.BookingRuleStrategy;
import soft.appointment.strategy.*;

/**
 *   manages the business logic for appointments. 
 *  handles booking, cancellations, and validation by communicating 
 * with the storage and rule systems.
 * * Authors: Moumen Abu Ayyash, Yamen Mashaqi, and Abdullah Zeidan
 */
public class AppointmentManager {
    
    private AppointmentStorage storage;
    private AppointmentRuleValidator calculator; 
/**
     * Default constructor
     * It automatically creates a new AppointmentStorage and AppointmentRuleCalculator
     * to handle the systems data and validation logic.
     */
    public AppointmentManager() {
        this(new AppointmentStorage(), new AppointmentRuleValidator());
    }
/**
     * Initialize the manager with a storage system and a rule validator.
     *
     * @param storage  storage  used to save and load appointments.
     * @param calculator validator used to validate business rules and restrictions.
     */
    public AppointmentManager(AppointmentStorage storage, AppointmentRuleValidator calculator) {
        this.storage = storage;
        this.calculator = calculator;
    }

    /**
     * check if a specific appointment slot follows the  time and duration rules.
     *  ensures the appointment is not in the past and meets the standard length.
     *
     * @param appt  appointment object containing the date, time, and duration.
     * @return  string "VALID" if all checks pass, or an error message explaining the failure.
     */
public String isSlotValid(Appointment appt) {
    java.time.LocalDateTime selected = java.time.LocalDateTime.of(appt.getDate(), appt.getStartTime());
    
    if (selected.isBefore(java.time.LocalDateTime.now())) {
        return "Error: Appointment date and time cannot be in the past.";
    }

    calculator.setStrategy(new DurationRule());
    if (!calculator.validate(appt)) {
        return calculator.getErrorMessage(); 
    }

    return "VALID"; 
}

    /**
     * get all appointments currently in the system.
     *
     * @return  list containing every appointment stored in the appointments file.
     */
    public List<Appointment> getAllSlots() {
    return storage.loadAllAppointments();
}
    /**
     * Sends an email reminder to every user signed up for an appointment.
     *  looks up each participant by their username and sends a formatted message.
     *
     * @param appt  appointment for which reminders are being sent.
     * @param nm  manager responsible for sending the actual notifications.
     * @param userStorage The storage  used to find user contact details.
     */
    public void sendReminders(Appointment appt, NotificationManager nm, UserStorage userStorage) {
    List<String> usernames = appt.getParticipants();
    
    for (String username : usernames) {
        User user = userStorage.getUserByUsername(username);
        
        if (user != null && user.getEmail() != null) {
            String msg = "Reminder: You have an appointment on " + 
                         appt.getDate() + " at " + appt.getStartTime();
            nm.notifyAll(user, msg);
        }
    }
}

    /**
 * Processes  booking for a specific slot.
 * It checks duration and capacity rules, applies type-specific restrictions, 
 * and ensures the user is not already booked at the same time.
 *
 * @param user The person who wants to join the appointment.
 * @param appt The appointment slot being booked.
 * @return SUCCESS if the booking is saved, otherwise a specific error message.
 */
  public String bookAppointment(User user, Appointment appt) {
    calculator.setStrategy(new DurationRule());
    if (!calculator.validate(appt)) {
        return calculator.getErrorMessage();
    }

    calculator.setStrategy(new CapacityRule());
    if (!calculator.validate(appt)) {
        return calculator.getErrorMessage();
    }

    BookingRuleStrategy typeStrategy = getStrategy(appt.gettype());
    if (typeStrategy != null && !typeStrategy.isValid(appt)) {
        return typeStrategy.getErrorMessage();
    }
        List<Appointment> myBookings = getMyBookings(user.getUsername());
  for (Appointment mine : myBookings) {
        if (mine.getDate().equals(appt.getDate()) && mine.getStartTime().equals(appt.getStartTime())) {
            return "Error: You already have another booking at this time!";
        }
    }

    appt.addParticipant(user.getUsername()); 

    if (appt.getCurrentParticipants() >= appt.getMaxParticipants()) {
        appt.setAvailable(false);
        appt.setStatus("Confirmed");
    } else {
        appt.setStatus("Partially Booked"); 
    }

    storage.deleteAppointment(appt);
    storage.saveAppointment(appt);
    
    return "SUCCESS";
}
/**
 *  returns all appointments that are still open for booking(doesn't check if the current user already in).
 * It goes through the entire list in storage and only collects those marked as available.
 *
 * @return A list of appointments that can currently be booked by users.
 */
    public List<Appointment> getAvailableSlots() {
        List<Appointment> all = storage.loadAllAppointments();
        List<Appointment> availableOnly = new ArrayList<>();
        for (Appointment a : all) {
            if (a.isAvailable()) {
                availableOnly.add(a);
            }
        }
        return availableOnly;
    }
    /**
 * Adds a new appointment slot  after  checks.
 * It verifies that the time is valid, ensures the appointment type follows 
 * its specific rules (like duration or participant limits), and checks 
 * that the new slot does not overlap with any existing appointments.
 *
 * @param appt The new appointment slot to be added to the system.
 * @return SUCCESS if the slot is saved, or a detailed error message if it fails any check.
 */
 public String addNewSlot(Appointment appt) {
       String timeValidation = isSlotValid(appt);
     if (!timeValidation.equals("VALID")) {
        return timeValidation;
    }

    BookingRuleStrategy strategy = getStrategy(appt.gettype());
    if (strategy != null && !strategy.isValid(appt)) {
        return strategy.getErrorMessage();
    }

    List<Appointment> all = storage.loadAllAppointments();
      java.time.LocalTime newStart = appt.getStartTime();
    java.time.LocalTime newEnd = newStart.plusMinutes(appt.getDuration());
    
 for (Appointment existing : all) {
        if (existing.getDate().equals(appt.getDate())) {
            java.time.LocalTime existingStart = existing.getStartTime();
            java.time.LocalTime existingEnd = existingStart.plusMinutes(existing.getDuration());

            
            if (newStart.isBefore(existingEnd) && existingStart.isBefore(newEnd)) {
                return "Error: This time range overlaps with an existing [" + 
                        existing.gettype() + "] appointment (" + existingStart + "-" + existingEnd + ")";
            }
        }
    }

    storage.saveAppointment(appt);
    return "SUCCESS";
}
/**
     * removes an appointment slot from the storage .
     *
     * @param appt The appointment slot that needs to be deleted.
     */
    public void removeSlot(Appointment appt)  {
        storage.deleteAppointment(appt);
    }
    /**
     * Cancels an  appointment slot and makes it available again.
     * This can only be done before the appointment date  
     * @param user The user requesting the cancellation.
     * @param appt The appointment slot to be cancelled.
     * @return true if the cancellation was successful, false if it was too late.
     */
    public boolean cancelAppointment(User user, Appointment appt) {
    LocalDate today = LocalDate.now();
    if (appt.getDate().isBefore(today) || appt.getDate().equals(today)) {
        return false; 
    }

    if (!user.getRole().equalsIgnoreCase("ADMIN") && !appt.getParticipants().contains(user.getUsername())) {
        return false; 
    }

    appt.setAvailable(true);
    appt.setStatus("Available");
    appt.getParticipants().clear(); 
    appt.setCurrentParticipants(0);

    storage.deleteAppointment(appt); 
    storage.saveAppointment(appt);   
    
    return true;
}
    /**
 *  all appointments that a specific user has joined.
 * It searches the entire file and returns a list of every slot 
 * where the user is listed as a participant.
 *
 * @param username The unique name of the user to search for.
 * @return A list of appointments the user is currently part of.
 */
    public List<Appointment> getMyBookings(String username) {
    List<Appointment> all = storage.loadAllAppointments();
    List<Appointment> myStuff = new ArrayList<>();
    for (Appointment a : all) {
        if (a.getParticipants().contains(username)) {
            myStuff.add(a);
        }
    }
    return myStuff;
}
    /**
 * remove a user from a specific appointment slot.
 *  method updates the participant list and the count, then 
 * marks the slot as available again so others can join.
 *
 * @param user The user who wants to leave the booking.
 * @param appt The appointment slot the user is leaving.
 * @return true if the booking was cancelled, false if the date has already passed.
 */
    public boolean cancelBooking(User user, Appointment appt) {
    if (appt.getDate().isBefore(java.time.LocalDate.now())) {
        return false; 
    }

    appt.removeParticipant(user.getUsername());
    
    appt.setCurrentParticipants(appt.getParticipants().size());
    
    appt.setAvailable(true);
    appt.setStatus("Available");

    storage.deleteAppointment(appt);
    storage.saveAppointment(appt);
    return true;
}
    /**
     * select the appropriate validation strategy based on the appointment type.
     * It cleans the input string to ensure it matches the correct rule set.
     *
     * @param type The category of the appointment ( virtual, urgent, so on).
     * @return The specific strategy object used to check rules, or null if not found.
     */
private BookingRuleStrategy getStrategy(String type){
    if (type == null) return null;

    return switch (type.toLowerCase().replace("-", ""))  {
    case "group" -> new GroupAppointmentStrategy();
    case "individual" -> new IndividualAppointmentStrategy();
    case "urgent" -> new UrgentAppointmentStrategy();
    case "followup" -> new FollowUpAppointmentStrategy();
    case "virtual" -> new VirtualAppointmentStrategy();
    case "assessment" -> new AssessmentAppointmentStrategy();
    case "inperson" -> new InPersonAppointmentStrategy();
    default -> null;
};


}
/**
     * Returns a list of open appointments that the specific user has not joined yet.
     * This prevents the user interface from showing slots the user is already part of.
     *
     * @param user The user looking for new appointments to book.
     * @return A list of available appointments that do not contain the user.
     */
    public List<Appointment> getAvailableSlotsForUser(User user) {
    List<Appointment> allAvailable = this.getAvailableSlots(); 
    List<Appointment> displayedSlots = new ArrayList<>();
    
    for (Appointment a : allAvailable) {
        if (!a.getParticipants().contains(user.getUsername())) {
            displayedSlots.add(a);
        }
    }
    return displayedSlots;
}
}