package soft.appointment.strategy;
import soft.appointment.domain.Appointment;
public class AssessmentAppointmentStrategy implements BookingRuleStrategy {

    @Override
    public boolean isValid(Appointment appt) {
        return appt.getDuration() == 60;
    }

    @Override
    public String getErrorMessage() {
        return "Error: Assessment appointments must be exactly 60 minutes long.";
    }
}
