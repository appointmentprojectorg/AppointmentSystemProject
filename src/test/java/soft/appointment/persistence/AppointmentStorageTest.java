/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soft.appointment.persistence;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import soft.appointment.domain.Appointment;

/**
 *
 * @author user
 */
public class AppointmentStorageTest {
    private AppointmentStorage storage = new AppointmentStorage("test_appointments.txt");

    @AfterEach
    void cleanup() {
        new File("test_appointments.txt").delete();
    }

    @Test
    void testSaveAndLoadAppointment() {
        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        storage.saveAppointment(appt);
        
        List<Appointment> list = storage.loadAllAppointments();
        assertEquals(1, list.size(), "Should have exactly one appointment");
        assertEquals(appt.getDate(), list.get(0).getDate());
        assertEquals(appt.getStartTime(), list.get(0).getStartTime());
    }
    @Test
void testLoadAllAppointmentsFileNotFound() {
    AppointmentStorage emptyStorage = new AppointmentStorage("non_existent_file.txt");
    List<Appointment> result = emptyStorage.loadAllAppointments();
    
    assertNotNull(result);
    assertTrue(result.isEmpty(), "Should return empty list if file doesn't exist");
}

 @Test
    void testDeleteExistingAppointment() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.of(10, 0);
        Appointment appt = new Appointment(date, time);
        storage.saveAppointment(appt);
        
        List<Appointment> initialList = storage.loadAllAppointments();
        assertEquals(1, initialList.size(), "Appointment should be saved initially");

        storage.deleteAppointment(appt);

        List<Appointment> afterDeleteList = storage.loadAllAppointments();
        assertTrue(afterDeleteList.isEmpty(), "Appointment list should be empty after deletion");
    }

    @Test
    void testDeleteNonExistentAppointment() {
        LocalDate date1 = LocalDate.now();
        LocalTime time1 = LocalTime.of(10, 0);
        Appointment existingAppt = new Appointment(date1, time1);
        storage.saveAppointment(existingAppt);

        LocalDate date2 = LocalDate.now().plusDays(1); // Different date
        LocalTime time2 = LocalTime.of(11, 0);
        Appointment nonExistentAppt = new Appointment(date2, time2);
        
        List<Appointment> initialList = storage.loadAllAppointments();
        assertEquals(1, initialList.size(), "Should have one appointment initially");

        storage.deleteAppointment(nonExistentAppt);

        List<Appointment> afterDeleteList = storage.loadAllAppointments();
        assertEquals(1, afterDeleteList.size(), "List size should remain 1 as non-existent appt was not deleted");
        assertEquals(existingAppt.getDate(), afterDeleteList.get(0).getDate());
        assertEquals(existingAppt.getStartTime(), afterDeleteList.get(0).getStartTime());
    }

    @Test
    void testDeleteAppointmentIOException() {
        
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.of(10, 0);
        Appointment appt = new Appointment(date, time);
        
        // Use a bad storage instance for the delete operation
        AppointmentStorage badStorage = new AppointmentStorage("C:/Windows/System32/invalid_file_for_delete.txt");
        
        
        assertDoesNotThrow(() -> badStorage.deleteAppointment(appt));
    }
}
