package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public final class AppointmentDAOImpl implements AppointmentDAO {
    @Override
    public String saveAppointment(AppointmentDTO appointment, Connection connection) {
        try {
            var ps = connection.prepareStatement("INSERT INTO appointment (appId,admId,cusName,cusMobile,dateTime) VALUES(?,?,?,?,?)");
            ps.setString(1, appointment.getAppId());
            ps.setString(2, appointment.getAdmId());
            ps.setString(3, appointment.getCusName());
            ps.setString(4, appointment.getCusMobile());
            ps.setString(5, appointment.getDateTime());

            if (ps.executeUpdate() != 0) {
                return "Appointment saved successful";
            } else {
                return "Appointment not saved";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
