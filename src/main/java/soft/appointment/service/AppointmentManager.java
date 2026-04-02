package soft.appointment.service;

import java.util.ArrayList;
import java.util.List;
import soft.appointment.domain.Appointment;
import soft.appointment.persistence.AppointmentStorage;

/**
 * Manages the business logic for appointments by talking to the Storage layer.
 * 
 * @author MoumenAbuAyyash1
 * @version 1.0
 */
public class AppointmentManager {
    
    private AppointmentStorage storage = new AppointmentStorage();

    /**
     * the method that returns only the appointment slots that are available.
     * 
     * @return a list of Appointment objects where available is true
     */
    public List<Appointment> getAvailableSlots() {
        List<Appointment> all = storage.loadAllAppointments();
        List<Appointment> availableOnly = new ArrayList<>();
        
        // add only add the ones that aren't booked yet
        for (Appointment a : all) {
            if (a.isAvailable()) {
                availableOnly.add(a);
            }
        }
        return availableOnly;
    }
     /**
     * Checks if an appointment slot is valid (e.g., in the future).
     * 
     * @param appt The appointment to validate
     * @return true if the appointment is in the future, false otherwise
     */
    public boolean isSlotValid(Appointment appt) {
        java.time.LocalDateTime selected = java.time.LocalDateTime.of(
            appt.getDate(), 
            appt.getStartTime()
        );
        
        // it has to be after the current time for it to add
         if (selected.isBefore(java.time.LocalDateTime.now())) {
        return false;
    }
          int minutes = appt.getStartTime().getMinute();
    if (minutes != 0 && minutes != 30) {
        return false; 
    }
        return true;

    }
    
    /**
     *  new appointment slot to the system.
     * 
     * @param appt the appointment object to be stored
     */
     public boolean addNewSlot(Appointment appt) {
        if (isSlotValid(appt)) {
            storage.saveAppointment(appt);
            return true;
        }
        return false;
    }
    
    /**
 *  for deleting an appointment slot.
 * @param appt The appointment to delete
 */
public void removeSlot(Appointment appt) {
    storage.deleteAppointment(appt);
}
}