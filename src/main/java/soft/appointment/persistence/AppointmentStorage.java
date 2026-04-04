package soft.appointment.persistence;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import soft.appointment.domain.Appointment;

/**
 * handles saving and loading appointments to appointments.txt.
 * 
 * @author MoumenAbuAyyash1
 * @version 1.0
 */
public class AppointmentStorage {
     private final String FILE_NAME = "appointments.txt";

    public void saveAppointment(Appointment appt) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {

            writer.println(
                appt.getDate() + "," +
                appt.getStartTime() + "," +
                appt.isAvailable() + "," +
                appt.getDuration() + "," +
                appt.getParticipants()
            );

        } catch (IOException e) {
        }
    }

    
    public List<Appointment> loadAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] p = line.split(",");

                Appointment appt = new Appointment(
                    LocalDate.parse(p[0]),
                    LocalTime.parse(p[1])
                );

                appt.setAvailable(Boolean.parseBoolean(p[2]));

               
                if (p.length > 3) {
                    appt.setDuration(Integer.parseInt(p[3]));
                } else {
                    appt.setDuration(30);
                }

                if (p.length > 4) {
                    appt.setParticipants(Integer.parseInt(p[4]));
                } else {
                    appt.setParticipants(0);
                }

                list.add(appt);
            }

        } catch (IOException e) {
        }

        return list;
    }

    public void deleteAppointment(Appointment target) {

        List<Appointment> all = loadAllAppointments();

        all.removeIf(a ->
            a.getDate().equals(target.getDate()) &&
            a.getStartTime().equals(target.getStartTime())
        );

        overwriteAll(all);
    }

    
    public void overwriteAll(List<Appointment> list) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, false))) {

            for (Appointment a : list) {

                writer.println(
                    a.getDate() + "," +
                    a.getStartTime() + "," +
                    a.isAvailable() + "," +
                    a.getDuration() + "," +
                    a.getParticipants()
                );
            }

        } catch (IOException e) {
        }
    }
   
}
