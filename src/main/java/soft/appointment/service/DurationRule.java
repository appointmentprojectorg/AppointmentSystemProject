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
       
        return mins == 0 || mins == 30;
    }
    
    @Override
    public String getErrorMessage() {
        return "Invalid Duration: Appointments must be booked in 30-minute slots (:00 or :30).";
    }
}
