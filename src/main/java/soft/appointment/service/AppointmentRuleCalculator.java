/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soft.appointment.service;
import soft.appointment.domain.Appointment;

/**
 *
 * @author user
 */
public class AppointmentRuleCalculator {
    private BookingRuleStrategy strategy;

    public void setStrategy(BookingRuleStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean validate(Appointment appt) {
        if (strategy == null) {
            throw new IllegalStateException("Rule strategy not set!");
        }
        return strategy.isValid(appt);
    }
    
    public String getErrorMessage() {
        return strategy.getErrorMessage();
    }
}
