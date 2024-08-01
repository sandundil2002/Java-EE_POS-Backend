package lk.ijse.elite_real_estate_posbackend.bo.impl;

import lk.ijse.elite_real_estate_posbackend.bo.AppointmentBO;
import lk.ijse.elite_real_estate_posbackend.dao.AppointmentDAOImpl;
import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;

import java.sql.Connection;

public class AppointmentBOImpl implements AppointmentBO {
    @Override
    public String saveAppointment(AppointmentDTO appointment, Connection connection) {
        AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();
        return appointmentDAO.saveAppointment(appointment, connection);
    }
}
