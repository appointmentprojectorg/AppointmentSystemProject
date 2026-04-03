package soft.appointment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import soft.appointment.domain.Appointment;
import soft.appointment.persistence.AppointmentStorage;
import soft.appointment.service.AppointmentManager;

@ExtendWith(MockitoExtension.class)
public class AppointmentManagerTest {

    @Mock
    private AppointmentStorage storage;

    @InjectMocks
    private AppointmentManager appointmentManager;

    @Test
    public void testTimeAndDateHandling() {
        Appointment appt = new Appointment(LocalDate.of(2026, 1, 10), LocalTime.of(10, 0));
        
        LocalDateTime past = LocalDateTime.of(2026, 1, 1, 12, 0);
        LocalDateTime future = LocalDateTime.of(2026, 1, 20, 12, 0);

        try (MockedStatic<LocalDateTime> mockedTime = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            mockedTime.when(LocalDateTime::now).thenReturn(past);
            assertTrue(appointmentManager.isSlotValid(appt));

            mockedTime.when(LocalDateTime::now).thenReturn(future);
            assertFalse(appointmentManager.isSlotValid(appt));
        }
    }

    @Test
    public void testMinutesValidation() {
        LocalDate futureDate = LocalDate.of(2099, 1, 1);
        
        assertTrue(appointmentManager.isSlotValid(new Appointment(futureDate, LocalTime.of(10, 0))));
        assertTrue(appointmentManager.isSlotValid(new Appointment(futureDate, LocalTime.of(10, 30))));
        assertFalse(appointmentManager.isSlotValid(new Appointment(futureDate, LocalTime.of(10, 15))));
    }

    @Test
    public void testAppointmentMinutesRuleWithoutStorage() {
        AppointmentManager manager = new AppointmentManager(null);
        LocalDate future = LocalDate.of(2099, 1, 1);

        // Valid: 00 and 30
        assertTrue(manager.isSlotValid(new Appointment(future, LocalTime.of(10, 0))));
        assertTrue(manager.isSlotValid(new Appointment(future, LocalTime.of(10, 30))));

        // Invalid: 15
        assertFalse(manager.isSlotValid(new Appointment(future, LocalTime.of(10, 15))));
    }

    @Test
    public void testGetAvailableSlots() {
        List<Appointment> all = new ArrayList<>();
        Appointment a1 = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        Appointment a2 = new Appointment(LocalDate.now(), LocalTime.of(11, 0));
        a2.setAvailable(false); 
        all.add(a1);
        all.add(a2);

        when(storage.loadAllAppointments()).thenReturn(all);

        List<Appointment> result = appointmentManager.getAvailableSlots();

        assertEquals(1, result.size());
        assertTrue(result.get(0).isAvailable());
    }
}