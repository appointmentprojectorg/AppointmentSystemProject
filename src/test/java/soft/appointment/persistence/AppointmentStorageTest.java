package soft.appointment.persistence;
import org.junit.jupiter.api.Test;
import soft.appointment.domain.Appointment;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class AppointmentStorageTest {
    private final String File_name="appointments.txt";
    @Test
    void testLoadAppointments_fileNotExist() {
        File file = new File(File_name);
        if (file.exists()) file.delete();

        AppointmentStorage storage = new AppointmentStorage();
        List<Appointment> list = storage.loadAllAppointments();

        assertTrue(list.isEmpty());
    }
    @Test
    void SaveAndLoadAppointment(){
        File file=new File(File_name);
        if(file.exists())file.delete();

        AppointmentStorage storage=new AppointmentStorage();
        Appointment appt = new Appointment(LocalDate.of(2026, 4, 3), LocalTime.of(10, 0));
        appt.setAvailable(true);
        storage.saveAppointment(appt);

        List<Appointment> list= storage.loadAllAppointments();
        assertEquals(1,list.size());
        assertEquals(LocalDate.of(2026,4,3),list.get(0).getDate());
        assertEquals(LocalTime.of(10,0),list.get(0).getStartTime());
        assertTrue(list.get(0).isAvailable());

            }

            @Test
            void testDeleteAppointment() {
                File file = new File(File_name);
                if (file.exists()) file.delete();

                AppointmentStorage storage = new AppointmentStorage();

                Appointment appt1 = new Appointment(LocalDate.of(2026, 4, 3), LocalTime.of(10, 0));
                Appointment appt2 = new Appointment(LocalDate.of(2026, 4, 4), LocalTime.of(12, 0));

                storage.saveAppointment(appt1);
                storage.saveAppointment(appt2);

                storage.deleteAppointment(appt1);
                List<Appointment> list = storage.loadAllAppointments();

                assertEquals(1, list.size());
                assertEquals(LocalDate.of(2026, 4, 4), list.get(0).getDate());
            }

}
