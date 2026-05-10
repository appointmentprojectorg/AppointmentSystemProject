package soft.appointment.strategy;
import  soft.appointment.domain.Appointment;
public class GroupAppointmentStrategy implements BookingRuleStrategy {
    @Override
    public boolean isValid(Appointment appointment){
        return appointment.getCurrentParticipants()<=appointment.getMaxParticipants();
    }

    @Override
    public String getErrorMessage() {
        return "Error: Group appointment has reached its maximum capacity.";
    }
}
