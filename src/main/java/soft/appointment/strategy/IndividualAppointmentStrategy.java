package soft.appointment.strategy;
import soft.appointment.domain.Appointment;
public class IndividualAppointmentStrategy implements BookingRuleStrategy {

    @Override
    public boolean isValid(Appointment appt){
        return appt.getCurrentParticipants()==1;
    }

    @Override
    public String getErrorMessage() {
        return "Error: Individual appointments are restricted to 1 participant only.";
    }
}
