package soft.appointment.strategy;
import soft.appointment.domain.Appointment;
public class VirtualAppointmentStrategy implements BookingRuleStrategy{

    @Override
    public boolean isValid(Appointment appt) {
        return appt.getMaxParticipants() <= 10;
    }

    @Override
    public String getErrorMessage() {
        return "Error: Virtual appointments are limited to a maximum of 10 participants.";
    }
}
