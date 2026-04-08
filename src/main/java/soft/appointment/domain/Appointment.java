package soft.appointment.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> participants = new ArrayList<>();
    private String type;
    private int duration;
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
public java.util.List<String> getParticipants() { 
    return participants; 
}

public void addParticipant(String username) {
    if (!participants.contains(username)) {
        this.participants.add(username);
        currentParticipants++;
    }
}
public void removeParticipant(String username) {
    this.participants.remove(username);
    currentParticipants--;
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
    public String gettype(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }
    public int getDuration(){
        return duration;
    }
    public void setdDuration(int duration){
        this.duration=duration;
    }
}

