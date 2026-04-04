/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soft.appointment.service;
import soft.appointment.domain.Appointment;


/**
 *
 * @author MoumenAbuAyyash1
 * this class is used to check if a booking is valid
 */
public interface  BookingRuleStrategy {
     /**
     * Checks if the appointment follows this specific rule.
     */
    boolean isValid(Appointment appointment);
    
    /**
     * Returns the error message if the rule is broken.
     */
    String getErrorMessage();
}
