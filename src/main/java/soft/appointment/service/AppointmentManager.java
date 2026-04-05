package soft.appointment.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import soft.appointment.domain.Appointment;
import soft.appointment.domain.User;
import soft.appointment.persistence.AppointmentStorage;

/**
 * Manages the business logic for appointments by talking to the Storage layer.
 * 
 * @author MoumenAbuAyyash1
 * @version 1.1
 */
public class AppointmentManager {
    
    private AppointmentStorage storage;
    private AppointmentRuleCalculator calculator; 

    public AppointmentManager() {
        this(new AppointmentStorage(), new AppointmentRuleCalculator());
    }

    public AppointmentManager(AppointmentStorage storage, AppointmentRuleCalculator calculator) {
        this.storage = storage;
        this.calculator = calculator;
    }

    /**
     * Checks if an appointment slot is valid for creation.
     */
    public boolean isSlotValid(Appointment appt) {
        java.time.LocalDateTime selected = java.time.LocalDateTime.of(appt.getDate(), appt.getStartTime());
        if (selected.isBefore(java.time.LocalDateTime.now())) {
            return false;
        }

        calculator.setStrategy(new DurationRule());
        if (!calculator.validate(appt)) {
            return false;
        }

        return true; 
    }
    
    public List<Appointment> getAllSlots() {
    // This returns the whole list from the file
    return storage.loadAllAppointments();
}

    /**
     * Processes a booking for a specific slot.
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

        appt.setCurrentParticipants(appt.getCurrentParticipants() + 1);
        
        if (appt.getCurrentParticipants() >= appt.getMaxParticipants()) {
            appt.setAvailable(false);
            appt.setStatus("Confirmed");
        }
        
    appt.addParticipant(user.getUsername()); 

        storage.deleteAppointment(appt);
        storage.saveAppointment(appt);
        
        return "SUCCESS";
    }

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
    
    public boolean addNewSlot(Appointment appt) {
        if (isSlotValid(appt)) {
            storage.saveAppointment(appt);
            return true;
        }
        return false;
    }

    public void removeSlot(Appointment appt) {
        storage.deleteAppointment(appt);
    }
    
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
}