/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soft.appointment.service;

import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import soft.appointment.domain.Appointment;

/**
 *
 * @author MoumenAbuAyyash1
 */
/*
public class CapacityRuleTest {
    private final CapacityRule rule = new CapacityRule();

    @Test
    void testCapacityNotReached() {
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        appt.setMaxParticipants(2);
        appt.setCurrentParticipants(1);
        assertTrue(rule.isValid(appt));
    }

    @Test
    void testCapacityFull() {
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        appt.setMaxParticipants(1);
        appt.setCurrentParticipants(1);
        assertFalse(rule.isValid(appt));
        assertNotNull(rule.getErrorMessage());
    }
}
*/