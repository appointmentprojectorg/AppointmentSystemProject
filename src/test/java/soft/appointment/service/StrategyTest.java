package soft.appointment.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import soft.appointment.domain.Appointment;
import soft.appointment.strategy.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class StrategyTest {

    @Test
    void testIndividualStrategy() {
        IndividualAppointmentStrategy strategy = new IndividualAppointmentStrategy();
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        appt.setCurrentParticipants(1);
        assertTrue(strategy.isValid(appt));
        appt.setCurrentParticipants(2);
        assertFalse(strategy.isValid(appt));
    }

    @Test
    void testFollowUpStrategy() {
        FollowUpAppointmentStrategy strategy = new FollowUpAppointmentStrategy();
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        appt.setDuration(30);
        assertTrue(strategy.isValid(appt));
        appt.setDuration(45);
        assertFalse(strategy.isValid(appt));
    }

    @Test
    void testInPersonStrategy() {
        InPersonAppointmentStrategy strategy = new InPersonAppointmentStrategy();
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        appt.setMaxParticipants(3);
        assertTrue(strategy.isValid(appt));
        appt.setMaxParticipants(4);
        assertFalse(strategy.isValid(appt));
    }

    @Test
    void testAssessmentStrategy() {
        AssessmentAppointmentStrategy strategy = new AssessmentAppointmentStrategy();
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        appt.setDuration(60);
        assertTrue(strategy.isValid(appt));
        appt.setDuration(30);
        assertFalse(strategy.isValid(appt));
    }

    @Test
    void testGroupStrategy() {
        GroupAppointmentStrategy strategy = new GroupAppointmentStrategy();
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        appt.setMaxParticipants(5);
        appt.setCurrentParticipants(3);
        assertTrue(strategy.isValid(appt));
        appt.setCurrentParticipants(6);
        assertFalse(strategy.isValid(appt));
    }

    @Test
    void testVirtualStrategy() {
        VirtualAppointmentStrategy strategy = new VirtualAppointmentStrategy();
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        appt.setMaxParticipants(10);
        assertTrue(strategy.isValid(appt));
        appt.setMaxParticipants(11);
        assertFalse(strategy.isValid(appt));
    }
}