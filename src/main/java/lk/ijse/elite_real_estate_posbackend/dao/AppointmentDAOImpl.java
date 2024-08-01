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

    @Override
    public boolean updateAppointment(String appId, AppointmentDTO appointment, Connection connection) {
        try {
            var ps = connection.prepareStatement("UPDATE appointment SET admId=?,cusName=?,cusMobile=?,dateTime=? WHERE appId=?");
            ps.setString(1, appointment.getAdmId());
            ps.setString(2, appointment.getCusName());
            ps.setString(3, appointment.getCusMobile());
            ps.setString(4, appointment.getDateTime());
            ps.setString(5, appId);
            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public AppointmentDTO searchAppointment(String appId, Connection connection) {
        try {
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            var ps = connection.prepareStatement("SELECT * FROM appointment WHERE appId=?");
            ps.setString(1, appId);
            var rst = ps.executeQuery();

            while (rst.next()) {
                appointmentDTO.setAppId(rst.getString("appId"));
                appointmentDTO.setAdmId(rst.getString("admId"));
                appointmentDTO.setCusName(rst.getString("cusName"));
                appointmentDTO.setCusMobile(rst.getString("cusMobile"));
                appointmentDTO.setDateTime(rst.getString("dateTime"));
            }
            return appointmentDTO;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
