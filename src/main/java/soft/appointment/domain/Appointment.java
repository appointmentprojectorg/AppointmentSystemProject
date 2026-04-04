package soft.appointment.domain;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * this represents a single appointment slot 
 * 
 * @author MoumenAbuAyyash1
 * @version 1.1
 */
public class Appointment {
    private LocalDate date;
    private LocalTime startTime;
    private boolean available;
     private String status;
    private int maxParticipants;
    private int currentParticipants;

    /**
     * Creates a new appointment slot.
     * 
     * @param date The date of the appointment
     * @param startTime The start time of the appointment
     */
    public Appointment(LocalDate date, LocalTime startTime) {
        this.date = date;
        this.startTime = startTime;
        this.available = true; // new slots start as available
        this.status = "Available";
        this.maxParticipants = 1; // default value ( 1 person)
        this.currentParticipants = 0;
    }

    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }
     public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }

    public int getCurrentParticipants() { return currentParticipants; }
    public void setCurrentParticipants(int currentParticipants) { this.currentParticipants = currentParticipants; }
}