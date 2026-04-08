package soft.appointment.strategy;
import soft.appointment.domain.Appointment;
public interface AppointmentRuleStrategy
{
    boolean isValid(Appointment appointment);
}
