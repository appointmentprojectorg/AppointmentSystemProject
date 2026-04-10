/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soft.appointment.service;
import soft.appointment.domain.Appointment;

/**
 *
 * @author MoumenAbuAyyash1
 */
public class DurationRule implements BookingRuleStrategy {
     @Override
public boolean isValid(Appointment appt) {
    int mins = appt.getStartTime().getMinute();
    boolean intervalValid = (mins == 0 || mins == 30);
    
    boolean durationValid = appt.getDuration() <= 30;
    
    return intervalValid && durationValid;
}

@Override
public String getErrorMessage() {
    return "Error: Appointments must start on the hour/half-hour and cannot exceed 30 minutes.";
}
}
