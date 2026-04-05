/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soft.appointment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

/**
 *
 * @author user
 */
/*
public class AppointmentRuleCalculatorTest {

    @Test
    void testValidateThrowsExceptionWhenStrategyIsNull() {
        AppointmentRuleCalculator calc = new AppointmentRuleCalculator();
        assertThrows(IllegalStateException.class, () -> calc.validate(null));
    }

    @Test
    void testValidateAndGetError() {
        AppointmentRuleCalculator calc = new AppointmentRuleCalculator();
        BookingRuleStrategy mockStrategy = Mockito.mock(BookingRuleStrategy.class);
        
        when(mockStrategy.isValid(any())).thenReturn(false);
        when(mockStrategy.getErrorMessage()).thenReturn("Error Message");
        
        calc.setStrategy(mockStrategy);
        
        assertFalse(calc.validate(null));
        assertEquals("Error Message", calc.getErrorMessage());
    }
}*/
