package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;

public sealed interface AppointmentBO permits AppointmentBOIMPL {
    String saveAppointment(AppointmentDTO appointment);
    boolean updateAppointment(String appId, AppointmentDTO appointment);
    AppointmentDTO searchAppointment(String appId);
    boolean deleteAppointment(String appId);
}
