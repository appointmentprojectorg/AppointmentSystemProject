package soft.appointment.strategy;
import soft.appointment.domain.Appointment;
public class AssessmentAppointmentStrategy implements AppointmentRuleStrategy {

    @Override
    public boolean isValid(Appointment appt) {
        return appt.getDuration() == 60;
    }
}
