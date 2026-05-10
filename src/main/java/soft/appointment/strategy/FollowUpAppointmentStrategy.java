package soft.appointment.strategy;
import  soft.appointment.domain.Appointment;
public class FollowUpAppointmentStrategy implements BookingRuleStrategy{
    @Override
    public boolean isValid(Appointment appt){
        return appt.getDuration()<=30;
    }

    @Override
    public String getErrorMessage() {
        return "Error: Follow-up appointments cannot exceed a 30-minute duration.";
    }
}
