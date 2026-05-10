/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soft.appointment.strategy;

import soft.appointment.domain.Appointment;

public interface BookingRuleStrategy {

    boolean isValid(Appointment appointment);

    String getErrorMessage();
}
