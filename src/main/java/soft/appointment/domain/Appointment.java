package soft.appointment.domain;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * this represents a single appointment slot 
 * 
 * @author MoumenAbuAyyash1
 * @version 1.0
 */
public class Appointment {
    private LocalDate date;
    private LocalTime startTime;
    private boolean available;
    private int duration; 
    private int participants;
    private int maxParticipants = 5; 


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
        this.duration = 30; // default
        this.participants = 0;

    }

    // Getters
    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public boolean isAvailable() { return available; }
    public int getDuration() { return duration; }
    public int getParticipants() { return participants; }
    public int getMaxParticipants() { return maxParticipants; }



    // Setters
    public void setAvailable(boolean available) { this.available = available; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setParticipants(int participants) { this.participants = participants; }

}
