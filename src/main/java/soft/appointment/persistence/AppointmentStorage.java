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
    
    private  String FILE_NAME = "appointments.txt";

    /**
     * Saves a single appointment to the file.
     * @param appt the appointment to save
     */
    public void saveAppointment(Appointment appt) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            // Format: date,time,isAvailable
            writer.println(appt.getDate() + "," + appt.getStartTime() + "," + appt.isAvailable());
        } catch (IOException e) { //
            
        }
    }
    
     public AppointmentStorage() {
    }

    public AppointmentStorage(String fileName) {
        this.FILE_NAME = fileName;
    }
    

    /**
     * Loads all appointments from the file into a List.
     * @return a List of Appointment objects
     */
    public List<Appointment> loadAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                // Convert the strings back into LocalDate and LocalTime objects
                Appointment appt = new Appointment(LocalDate.parse(p[0]), LocalTime.parse(p[1]));
                appt.setAvailable(Boolean.parseBoolean(p[2]));
                list.add(appt);
            }
        } catch (IOException e) {
        }
        return list;
    }
    /**
 * Removes a specific appointment from the storage file.
 * @param target The appointment to be removed
 */
public void deleteAppointment(Appointment target) {
    List<Appointment> all = loadAllAppointments();
    all.removeIf(a -> a.getDate().equals(target.getDate()) && 
                      a.getStartTime().equals(target.getStartTime()));
    
    try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, false))) {
        for (Appointment a : all) {
            writer.println(a.getDate() + "," + a.getStartTime() + "," + a.isAvailable());
        }
    } catch (IOException e) {
    }
}
}