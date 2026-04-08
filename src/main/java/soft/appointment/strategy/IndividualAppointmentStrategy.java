package soft.appointment.strategy;
import soft.appointment.domain.Appointment;
public class IndividualAppointmentStrategy implements AppointmentRuleStrategy {

    @Override
    public boolean isValid(Appointment appt){
        return appt.getCurrentParticipants()==1;
    }
}
