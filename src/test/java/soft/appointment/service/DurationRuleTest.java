package soft.appointment.service;

import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import soft.appointment.domain.Appointment;

public class DurationRuleTest {
    private final DurationRule rule = new DurationRule();

     @Test
    void testValidDuration() {
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        appt.setDuration(30); 
        assertTrue(rule.isValid(appt));
    }

    @Test
    void testInvalidDuration() {
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(11, 00));
        appt.setDuration(45); 
        assertFalse(rule.isValid(appt));
        assertNotNull(rule.getErrorMessage());
    }

    @Test
    void testAssessmentExemption() {
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        appt.setType("Assessment");
        appt.setDuration(60); 
        assertTrue(rule.isValid(appt));
    }
}