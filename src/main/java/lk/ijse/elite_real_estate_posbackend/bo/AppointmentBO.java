package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;

import java.sql.Connection;

public interface AppointmentBO {
    String saveAppointment(AppointmentDTO appointment, Connection connection);
}
