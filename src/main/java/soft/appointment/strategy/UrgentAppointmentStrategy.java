package soft.appointment.strategy;
import  soft.appointment.domain.Appointment;
import java.time.LocalDate;
public class UrgentAppointmentStrategy implements AppointmentRuleStrategy{
  @Override
    public boolean isValid(Appointment appt) {
        LocalDate today = LocalDate.now();
        return !appt.getDate().isBefore(today) && 
               appt.getDate().isBefore(today.plusDays(3));
    }
}
