package soft.appointment.strategy;
import  soft.appointment.domain.Appointment;
public class GroupAppointmentStrategy implements AppointmentRuleStrategy {
    @Override
    public boolean isValid(Appointment appointment){
        return appointment.getCurrentParticipants()<=appointment.getMaxParticipants();
    }
}
