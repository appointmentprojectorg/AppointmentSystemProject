package soft.appointment.strategy;
import soft.appointment.domain.Appointment;
public class InPersonAppointmentStrategy implements AppointmentRuleStrategy {

    @Override
    public boolean isValid(Appointment appt) {
        return appt.getCurrentParticipants() <= 3;
    }
}
