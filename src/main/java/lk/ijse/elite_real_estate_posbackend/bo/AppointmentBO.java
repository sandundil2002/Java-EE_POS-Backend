package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;

import java.sql.Connection;

public interface AppointmentBO {
    String saveAppointment(AppointmentDTO appointment, Connection connection);
    boolean updateAppointment(String appId, AppointmentDTO appointment, Connection connection);
    AppointmentDTO searchAppointment(String appId, Connection connection);
    boolean deleteAppointment(String appId, Connection connection);
}
