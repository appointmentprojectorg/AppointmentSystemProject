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
public class CapacityRule implements BookingRuleStrategy {
     @Override
    public boolean isValid(Appointment appt) {
        // PDF Requirement: Capacity rules respected
        return appt.getCurrentParticipants() < appt.getMaxParticipants();
    }
@Override
    public String getErrorMessage() {
        return "Capacity Exceeded: This appointment slot is already full!";
    }
}
