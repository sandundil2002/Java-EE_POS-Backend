package lk.ijse.elite_real_estate_posbackend.bo.impl;

import lk.ijse.elite_real_estate_posbackend.bo.AppointmentBO;
import lk.ijse.elite_real_estate_posbackend.dao.AppointmentDAOImpl;
import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;

import java.sql.Connection;

public class AppointmentBOImpl implements AppointmentBO {
    private final AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();

    @Override
    public String saveAppointment(AppointmentDTO appointment, Connection connection) {
        return appointmentDAO.saveAppointment(appointment, connection);
    }

    @Override
    public boolean updateAppointment(String appId, AppointmentDTO appointment, Connection connection) {
        return appointmentDAO.updateAppointment(appId, appointment, connection);
    }
}
