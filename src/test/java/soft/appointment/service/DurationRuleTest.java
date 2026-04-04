/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soft.appointment.service;

import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import soft.appointment.domain.Appointment;

/**
 *
 * @author MoumenAbuAyyash1
 */
public class DurationRuleTest {
    private final DurationRule rule = new DurationRule();

    @Test
    void testValidMinutes() {
        Appointment appt00 = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        Appointment appt30 = new Appointment(LocalDate.now(), LocalTime.of(10, 30));
        assertTrue(rule.isValid(appt00));
        assertTrue(rule.isValid(appt30));
    }

    @Test
    void testInvalidMinutes() {
        Appointment appt15 = new Appointment(LocalDate.now(), LocalTime.of(10, 15));
        assertFalse(rule.isValid(appt15));
        assertEquals("Invalid Duration: Appointments must be booked in 30-minute slots (:00 or :30).", rule.getErrorMessage());
    }
}
