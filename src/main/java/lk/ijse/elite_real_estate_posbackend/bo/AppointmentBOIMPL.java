package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dao.AppointmentDAOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;

public final class AppointmentBOIMPL implements AppointmentBO {
    private final AppointmentDAOIMPL appointmentDAO = new AppointmentDAOIMPL();

    @Override
    public String saveAppointment(AppointmentDTO appointment) {
        return appointmentDAO.saveAppointment(appointment);
    }

    @Override
    public boolean updateAppointment(String appId, AppointmentDTO appointment) {
        return appointmentDAO.updateAppointment(appId, appointment);
    }

    @Override
    public AppointmentDTO searchAppointment(String appId) {
        return appointmentDAO.searchAppointment(appId);
    }

    @Override
    public boolean deleteAppointment(String appId ) {
        return appointmentDAO.deleteAppointment(appId);
    }
}
