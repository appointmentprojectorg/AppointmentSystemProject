package soft.appointment.persistence;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import soft.appointment.domain.Appointment;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class AppointmentStorageTest {

    private final String TEST_FILE = "test_appointments.txt";
    private AppointmentStorage storage;

    @BeforeEach
    void setUp() {
        storage = new AppointmentStorage(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testSaveAndLoad() {
        Appointment appt = new Appointment(LocalDate.of(2025, 5, 20), LocalTime.of(10, 0));
        appt.setType("Virtual");
        appt.setDuration(45);
        appt.addParticipant("testUser");

        storage.saveAppointment(appt);

        List<Appointment> results = storage.loadAllAppointments();

        assertEquals(1, results.size(), "Should have exactly 1 appointment.");
        Appointment saved = results.get(0);
        assertEquals("Virtual", saved.gettype());
        assertEquals(45, saved.getDuration());
        assertTrue(saved.getParticipants().contains("testUser"));
    }

    @Test
    void testDeleteAppointment() {
        Appointment a1 = new Appointment(LocalDate.now(), LocalTime.of(9, 0));
        Appointment a2 = new Appointment(LocalDate.now(), LocalTime.of(10, 0));

        storage.saveAppointment(a1);
        storage.saveAppointment(a2);

        storage.deleteAppointment(a1);

        List<Appointment> remaining = storage.loadAllAppointments();
        assertEquals(1, remaining.size());
        assertEquals(LocalTime.of(10, 0), remaining.get(0).getStartTime());
    }

    
    @Test
    void testLoadFileNotFound() {
        new File(TEST_FILE).delete();
        
        List<Appointment> results = storage.loadAllAppointments();
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    
    @Test
    void testParticipantSplitting() {
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(11, 0));
        appt.addParticipant("UserA");
        appt.addParticipant("UserB");
        appt.addParticipant("UserC");

        storage.saveAppointment(appt);

        List<Appointment> results = storage.loadAllAppointments();
        assertEquals(3, results.get(0).getParticipants().size());
        assertTrue(results.get(0).getParticipants().contains("UserB"));
    }
}