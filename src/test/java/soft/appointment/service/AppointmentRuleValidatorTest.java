package soft.appointment.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import soft.appointment.domain.Appointment;
import soft.appointment.strategy.BookingRuleStrategy;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.mockito.Mockito.when;

public class AppointmentRuleValidatorTest {

    @Test
    void testValidateThrowsExceptionWhenStrategyIsNull() {
        AppointmentRuleValidator calc = new AppointmentRuleValidator();
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.now());
        assertThrows(IllegalStateException.class, () -> calc.validate(appt));
    }

    @Test
    void testValidateAndGetError() {
        AppointmentRuleValidator calc = new AppointmentRuleValidator();
        BookingRuleStrategy mockStrategy = Mockito.mock(BookingRuleStrategy.class);
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.now());

        when(mockStrategy.isValid(appt)).thenReturn(false);
        when(mockStrategy.getErrorMessage()).thenReturn("Error Message");

        calc.setStrategy(mockStrategy);

        assertFalse(calc.validate(appt));
        assertEquals("Error Message", calc.getErrorMessage());
    }
}