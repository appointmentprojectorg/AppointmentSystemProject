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
    
    private  String fileName = "appointments.txt";

    /**
     * Saves a single appointment to the file.
     * @param appt the appointment to save
     */
   public void saveAppointment(Appointment appt) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
        String participantNames = String.join(";", appt.getParticipants());
        
        if (participantNames.isEmpty()) {
            participantNames = "NONE";
        }

        writer.println(appt.getDate() + "," + 
               appt.getStartTime() + "," + 
               appt.isAvailable() + "," + 
               appt.getStatus() + "," + 
               appt.getMaxParticipants() + "," + 
               appt.getCurrentParticipants() + "," + 
               participantNames + "," +
               appt.gettype() + "," +           
               appt.getDuration());             
    } catch (IOException e) { 
    }
}
    
     public AppointmentStorage() {
    }

    public AppointmentStorage(String fileName) {
        this.fileName = fileName;
    }
    

    /**
     * Loads all appointments from the file into a List.
     * @return a List of Appointment objects
     */
    public List<Appointment> loadAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        File file = new File(fileName);
        
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
    String[] p = line.split(",");
    Appointment appt = new Appointment(LocalDate.parse(p[0]), LocalTime.parse(p[1]));
    appt.setAvailable(Boolean.parseBoolean(p[2]));
    
    appt.setStatus(p[3]);
    appt.setMaxParticipants(Integer.parseInt(p[4]));
    
     String[] names = p[6].split(";");
    for (String n : names) {
        if (!n.equals("NONE") && !n.trim().isEmpty()) {
            appt.addParticipant(n);
        }
    }
    if (p.length >= 8) {
    appt.setType(p[7]); 
}
if (p.length >= 9) {
    appt.setDuration(Integer.parseInt(p[8])); 
}
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
    
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
        for (Appointment a : all) {
            String names = String.join(";", a.getParticipants());
            if (names.isEmpty()) names = "NONE";

           writer.println(a.getDate() + "," + a.getStartTime() + "," + a.isAvailable() + "," +
               a.getStatus() + "," + a.getMaxParticipants() + "," + a.getCurrentParticipants()
               + "," + names + "," + a.gettype() + "," + a.getDuration());
        }
    } catch (IOException e) {
    }
}
}