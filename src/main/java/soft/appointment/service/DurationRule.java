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
       
        if ("Assessment".equalsIgnoreCase(appt.gettype())) {
            return true; 
        }
        
        
        return appt.getDuration() > 0 && appt.getDuration() <= 30;
    }

    @Override
    public String getErrorMessage() {
        return "Error: Appointment duration cannot exceed 30 minutes (except Assessments).";
    }
}
