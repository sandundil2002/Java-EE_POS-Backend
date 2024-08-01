package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;

import java.sql.Connection;

public sealed interface AppointmentDAO permits AppointmentDAOImpl{
    String saveAppointment(AppointmentDTO appointmentDTO, Connection connection);
    boolean updateAppointment(String appId, AppointmentDTO appointment, Connection connection);
    AppointmentDTO searchAppointment(String appId, Connection connection);
}
