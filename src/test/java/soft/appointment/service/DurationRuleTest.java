package soft.appointment.service;

import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import soft.appointment.domain.Appointment;

public class DurationRuleTest {
    private final DurationRule rule = new DurationRule();

    @Test
    void testFullValid() {
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 30));
        appt.setDuration(30);
        assertTrue(rule.isValid(appt));
    }

    @Test
    void testInvalidMinutes() {
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 15));
        assertFalse(rule.isValid(appt));
    }

    @Test
    void testInvalidDuration() {
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(11, 00));
        appt.setDuration(45);
        assertFalse(rule.isValid(appt));
        assertNotNull(rule.getErrorMessage());
    }
}