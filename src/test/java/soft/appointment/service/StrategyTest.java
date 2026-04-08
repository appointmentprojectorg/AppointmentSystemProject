package soft.appointment.service;

import org.junit.jupiter.api.Test;
import soft.appointment.domain.Appointment;
import soft.appointment.strategy.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {

    @Test
    void testGroupAppointment_valid() {
        GroupAppointmentStrategy strategy = new GroupAppointmentStrategy();

        Appointment appt = new Appointment(
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0)
        );

        appt.setMaxParticipants(5);
        appt.setCurrentParticipants(3);

        assertTrue(strategy.isValid(appt));
    }

    @Test
    void testGroupAppointment_invalid() {
        GroupAppointmentStrategy strategy = new GroupAppointmentStrategy();

        Appointment appt = new Appointment(
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0)
        );
        appt.setMaxParticipants(5);
        appt.setCurrentParticipants(10);
        assertFalse(strategy.isValid(appt));
    }
    @Test
    void testIndividualAppointment() {
        IndividualAppointmentStrategy strategy = new IndividualAppointmentStrategy();

        Appointment appt = new Appointment(
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0)
        );

        appt.setCurrentParticipants(1);
        assertTrue(strategy.isValid(appt));
        appt.setCurrentParticipants(2);
        assertFalse(strategy.isValid(appt));
    }
    @Test
    void testUrgentAppointment() {
        UrgentAppointmentStrategy strategy = new UrgentAppointmentStrategy();

        Appointment appt = new Appointment(
                LocalDate.now(),
                LocalTime.of(10, 0)
        );
        assertTrue(strategy.isValid(appt));
        appt = new Appointment(
                LocalDate.now().plusDays(5),
                LocalTime.of(10, 0)
        );
        assertFalse(strategy.isValid(appt));
    }
    @Test
    void testFollowUpAppointment() {
        FollowUpAppointmentStrategy strategy = new FollowUpAppointmentStrategy();
        Appointment appt = new Appointment(
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0)
        );
        appt.setdDuration(30);
        assertTrue(strategy.isValid(appt));
        appt.setdDuration(60);
        assertFalse(strategy.isValid(appt));
    }
    


}