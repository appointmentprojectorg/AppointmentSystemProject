package soft.appointment.strategy;
import soft.appointment.domain.Appointment;
public class InPersonAppointmentStrategy implements BookingRuleStrategy {

    @Override
    public boolean isValid(Appointment appt) {
        return appt.getMaxParticipants() <= 3;
    }

    @Override
    public String getErrorMessage() {
        return "Error: In-person appointments are limited to a maximum of 3 participants.";
    }
}
