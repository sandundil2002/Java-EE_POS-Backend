package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dao.AppointmentDAOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;

import java.sql.Connection;

public final class AppointmentBOIMPL implements AppointmentBO {
    private final AppointmentDAOIMPL appointmentDAO = new AppointmentDAOIMPL();

    @Override
    public String saveAppointment(AppointmentDTO appointment, Connection connection) {
        return appointmentDAO.saveAppointment(appointment, connection);
    }

    @Override
    public boolean updateAppointment(String appId, AppointmentDTO appointment, Connection connection) {
        return appointmentDAO.updateAppointment(appId, appointment, connection);
    }

    @Override
    public AppointmentDTO searchAppointment(String appId, Connection connection) {
        return appointmentDAO.searchAppointment(appId, connection);
    }

    @Override
    public boolean deleteAppointment(String appId, Connection connection) {
        return appointmentDAO.deleteAppointment(appId, connection);
    }
}
