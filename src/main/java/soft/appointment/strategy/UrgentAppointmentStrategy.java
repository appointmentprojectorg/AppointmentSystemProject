package soft.appointment.strategy;
import  soft.appointment.domain.Appointment;
import java.time.LocalDate;
public class UrgentAppointmentStrategy implements AppointmentRuleStrategy{
    @Override
    public boolean isValid(Appointment appt){
        LocalDate today=LocalDate.now();
        return appt.getDate().equals(today)||appt.getDate().equals(today.plusDays(1));
    }
}
