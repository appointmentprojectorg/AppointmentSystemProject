package soft.appointment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import soft.appointment.domain.Appointment;
import soft.appointment.domain.User;
import soft.appointment.persistence.AppointmentStorage;
import soft.appointment.persistence.UserStorage;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentManagerTest {

    private AppointmentManager appointmentManager;
    private AppointmentStorage storage;
    private User testUser;
    private User adminUser;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        String testFileName = tempDir.resolve("test_appointments.txt").toString();
        storage = new AppointmentStorage(testFileName);
        appointmentManager = new AppointmentManager(storage, new AppointmentRuleCalculator());
        
        testUser = new User("moumen", "pw", "USER", "moumen@test.com");
        adminUser = new User("admin", "adminpw", "ADMIN", "admin@test.com");
    }

    @Test
    void testConstructors() {
        AppointmentManager amDefault = new AppointmentManager();
        assertNotNull(amDefault);
        
        AppointmentManager amManual = new AppointmentManager(storage, new AppointmentRuleCalculator());
        assertNotNull(amManual);
    }

    @Test
void testIsSlotValid_Coverage() {
    Appointment past = new Appointment(LocalDate.now().minusDays(1), LocalTime.of(10, 0));
    assertEquals("Error: Appointment date and time cannot be in the past.", appointmentManager.isSlotValid(past));

    Appointment invalidDuration = new Appointment(LocalDate.now().plusDays(1), LocalTime.of(10, 15));
    invalidDuration.setDuration(45); 
    assertTrue(appointmentManager.isSlotValid(invalidDuration).contains("cannot exceed 30 minutes"));

    Appointment valid = new Appointment(LocalDate.now().plusDays(1), LocalTime.of(10, 30));
    valid.setDuration(30);
    assertEquals("VALID", appointmentManager.isSlotValid(valid));
}

    @Test
    void testBookAppointment_BusinessLogic() {
        Appointment appt = new Appointment(LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        appt.setMaxParticipants(2); 
        storage.saveAppointment(appt);

        String result = appointmentManager.bookAppointment(testUser, appt);
        assertEquals("SUCCESS", result);
        assertEquals("Partially Booked", appt.getStatus());

        String doubleRes = appointmentManager.bookAppointment(testUser, appt);
        assertEquals("Error: You already have another booking at this time!", doubleRes);

        User user2 = new User("ali", "pw", "USER", "ali@test.com");
        String result2 = appointmentManager.bookAppointment(user2, appt);
        assertEquals("SUCCESS", result2);
        assertEquals("Confirmed", appt.getStatus());
        assertFalse(appt.isAvailable());

        User user3 = new User("sami", "pw", "USER", "sami@test.com");
        String capRes = appointmentManager.bookAppointment(user3, appt);
        assertEquals("Capacity Exceeded: This appointment slot is already full!", capRes);
    }

    @Test
    void testAddNewSlot_OverlapAndSwitchCoverage() {
        LocalDate futureDate = LocalDate.now().plusDays(2);

        Appointment existing = new Appointment(futureDate, LocalTime.of(9, 0));
        existing.setType("Assessment");
        existing.setDuration(60); 
        storage.saveAppointment(existing);

        Appointment overlapping = new Appointment(futureDate, LocalTime.of(9, 30));
        overlapping.setDuration(30);
        String overlapRes = appointmentManager.addNewSlot(overlapping);
        assertTrue(overlapRes.contains("overlaps with an existing [Assessment] appointment (09:00-10:00)"));

        Appointment v = new Appointment(futureDate, LocalTime.of(11, 0));
        v.setType("virtual");
        v.setMaxParticipants(15);
        assertEquals("Error: Virtual appointments are limited to a maximum of 10 participants.", appointmentManager.addNewSlot(v));

       Appointment u = new Appointment(LocalDate.now().plusDays(10), LocalTime.of(12, 0));
u.setType("urgent");
assertEquals("Error: Urgent appointments must be scheduled within the next 3 days.", appointmentManager.addNewSlot(u));

        Appointment g = new Appointment(futureDate, LocalTime.of(14, 0));
        g.setType("group");
        g.setMaxParticipants(-1); 
        assertEquals("Error: The selected appointment type violates system business rules.", appointmentManager.addNewSlot(g));
    }

    @Test
    void testCancelAppointment_AdminAndUserLogic() {
        LocalDate future = LocalDate.now().plusDays(5);
        Appointment futureAppt = new Appointment(future, LocalTime.of(10, 0));
        futureAppt.addParticipant(testUser.getUsername());
        storage.saveAppointment(futureAppt);

        User stranger = new User("stranger", "pw", "USER", "s@test.com");
        assertFalse(appointmentManager.cancelAppointment(stranger, futureAppt));

        assertTrue(appointmentManager.cancelAppointment(adminUser, futureAppt));
        assertEquals("Available", futureAppt.getStatus());

        Appointment todayAppt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        assertFalse(appointmentManager.cancelAppointment(adminUser, todayAppt));
    }

    @Test
    void testCancelBooking_And_Reminders() {
        Appointment appt = new Appointment(LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        appt.addParticipant(testUser.getUsername());
        assertTrue(appointmentManager.cancelBooking(testUser, appt));

        NotificationManager mockNm = mock(NotificationManager.class);
        UserStorage mockUsers = mock(UserStorage.class);
        
        when(mockUsers.getUserByUsername(testUser.getUsername())).thenReturn(testUser);
        
        appointmentManager.sendReminders(appt, mockNm, mockUsers);
        appt.addParticipant(testUser.getUsername());
        appointmentManager.sendReminders(appt, mockNm, mockUsers);
        verify(mockNm, atLeastOnce()).notifyAll(any(), anyString());
    }

    @Test
    void testGetAvailableSlots_Filtering() {
        Appointment a1 = new Appointment(LocalDate.now().plusDays(1), LocalTime.of(9, 0));
        a1.setAvailable(true);
        Appointment a2 = new Appointment(LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        a2.setAvailable(false);
        
        storage.saveAppointment(a1);
        storage.saveAppointment(a2);
        
        List<Appointment> results = appointmentManager.getAvailableSlots();
        assertEquals(1, results.size());
        assertTrue(results.get(0).isAvailable());
    }

    @Test
    void testGetMyBookings() {
        Appointment appt = new Appointment(LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        appt.addParticipant("moumen");
        storage.saveAppointment(appt);
        
        List<Appointment> mine = appointmentManager.getMyBookings("moumen");
        assertEquals(1, mine.size());
    }
    @Test
    void testSendRemindersInteraction() {
        NotificationManager mockNm = mock(NotificationManager.class);
        UserStorage mockStorage = mock(UserStorage.class);
        
        User user = new User("moumen", "pw", "USER", "moumen@test.com");
        when(mockStorage.getUserByUsername("moumen")).thenReturn(user);

        Appointment appt = new Appointment(LocalDate.now(), LocalTime.of(10, 0));
        appt.addParticipant("moumen");

        appointmentManager.sendReminders(appt, mockNm, mockStorage);

        verify(mockNm, times(1)).notifyAll(eq(user), anyString());
    }
   @Test
void testGetAvailableSlotsForUser_Refactored() {
    User student = new User("moumen", "pw", "USER", "moumen@test.com");
    
    Appointment alreadyJoined = new Appointment(LocalDate.now().plusDays(1), LocalTime.of(14, 0));
    alreadyJoined.addParticipant("moumen");
    alreadyJoined.setAvailable(true); 
    
    Appointment eligibleSlot = new Appointment(LocalDate.now().plusDays(1), LocalTime.of(15, 0));
    eligibleSlot.setAvailable(true); 
    
    storage.saveAppointment(alreadyJoined);
    storage.saveAppointment(eligibleSlot);

    List<Appointment> results = appointmentManager.getAvailableSlotsForUser(student);

    assertNotNull(results);
    assertEquals(1, results.size());

    boolean foundEligible = false;
    for (Appointment a : results) {
        if (a.getDate().equals(eligibleSlot.getDate()) && 
            a.getStartTime().equals(eligibleSlot.getStartTime())) {
            foundEligible = true;
        }
        
        assertFalse(a.getParticipants().contains("moumen")
            );
    }
    
    assertTrue(foundEligible);
}
}