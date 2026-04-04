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

    public boolean isSlotValid(Appointment appt) {

        java.time.LocalDateTime selected = java.time.LocalDateTime.of(
            appt.getDate(),
            appt.getStartTime()
        );

        if (selected.isBefore(java.time.LocalDateTime.now())) {
            return false;
        }

        int minutes = appt.getStartTime().getMinute();
        if (minutes != 0 && minutes != 30) {
            return false;
        }

        return true;
    }

    public boolean addNewSlot(Appointment appt) {

        if (!isSlotValid(appt)) {
            return false;
        }

        List<Appointment> all = storage.loadAllAppointments();

        for (Appointment a : all) {
            if (a.getDate().equals(appt.getDate()) &&
                a.getStartTime().equals(appt.getStartTime())) {
                return false;
            }
        }

        storage.saveAppointment(appt);
        return true;
    }

    
    public void removeSlot(Appointment appt) {
        storage.deleteAppointment(appt);
    }

    public String bookAppointment(Appointment target, int duration, int participants) {

        List<Appointment> all = storage.loadAllAppointments();

        for (Appointment a : all) {

            if (a.getDate().equals(target.getDate()) &&
                a.getStartTime().equals(target.getStartTime())) {

                
                if (!a.isAvailable()) {
                    return "This slot is already booked!";
                }

                
                if (duration != 30 && duration != 60) {
                    return "Invalid duration! Only 30 or 60 minutes allowed.";
                }

           
                if (participants > a.getMaxParticipants()) {
                    return "Too many participants! Max is " + a.getMaxParticipants();
                }

                
                a.setAvailable(false);
                a.setDuration(duration);
                a.setParticipants(participants);

               
                storage.overwriteAll(all);

                return "Booking Confirmed!";
            }
        }

        return "Slot not found!";
    }
   
}
