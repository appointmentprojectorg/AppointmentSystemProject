package soft.appointment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soft.appointment.domain.Appointment;
import soft.appointment.persistence.AppointmentStorage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AppointmentManagerTest {

    @Mock
    private AppointmentStorage storage;
    @Mock
    private AppointmentRuleCalculator calculator; 
    @InjectMocks
    private AppointmentManager appointmentManager;

    @Test
    public void testIsSlotValid_PastDate() {
        Appointment pastAppt = new Appointment(LocalDate.now().minusDays(1), LocalTime.of(10, 0));
        assertFalse(appointmentManager.isSlotValid(pastAppt));
    }

    @Test
    public void testIsSlotValid_FutureInvalidDuration() {
        Appointment appt = new Appointment(LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        when(calculator.validate(appt)).thenReturn(false); 
        assertFalse(appointmentManager.isSlotValid(appt));
    }

    @Test
    public void testAddNewSlot_Success() {
        Appointment appt = new Appointment(LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        when(calculator.validate(appt)).thenReturn(true);
        
        assertTrue(appointmentManager.addNewSlot(appt));
        verify(storage).saveAppointment(appt);
    }

    @Test
    public void testAddNewSlot_Fail() {
        Appointment appt = new Appointment(LocalDate.now().minusDays(1), LocalTime.of(10, 0));
        assertFalse(appointmentManager.addNewSlot(appt));
        verify(storage, never()).saveAppointment(any());
    }

    @Test
    public void testGetAvailableSlots() {
        List<Appointment> all = new ArrayList<>();
        Appointment a1 = new Appointment(LocalDate.now(), LocalTime.of(10, 0)); 
        Appointment a2 = new Appointment(LocalDate.now(), LocalTime.of(11, 0));
        a2.setAvailable(false); // Booked
        all.add(a1);
        all.add(a2);

        when(storage.loadAllAppointments()).thenReturn(all);

        List<Appointment> result = appointmentManager.getAvailableSlots();
        assertEquals(1, result.size());
        assertTrue(result.get(0).isAvailable());
    }

    @Test
    public void testRemoveSlot() {
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        appointmentManager.removeSlot(appt);
        verify(storage).deleteAppointment(appt);
    }
    
    @Test
    public void testBookAppointment_Success() {
        Appointment appt = new Appointment(LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        appt.setMaxParticipants(1);
        appt.setCurrentParticipants(0);
        
        when(calculator.validate(appt)).thenReturn(true);

        String result = appointmentManager.bookAppointment(appt);

        assertEquals("SUCCESS", result);
        verify(storage).saveAppointment(appt);
    }
}