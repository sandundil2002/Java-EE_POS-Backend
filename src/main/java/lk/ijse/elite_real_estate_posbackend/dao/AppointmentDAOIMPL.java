package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;
import lk.ijse.elite_real_estate_posbackend.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public final class AppointmentDAOIMPL implements AppointmentDAO {
    private final Connection connection = ConnectionUtil.getInstance().getConnection();

    public List<AppointmentDTO> getAllAppointments() {
        List<AppointmentDTO> appointments = new ArrayList<>();

        try {
            var pst = connection.prepareStatement( "SELECT * FROM appointment");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                AppointmentDTO appointment = new AppointmentDTO(
                        rs.getString("App_id"),
                        rs.getString("Adm_id"),
                        rs.getString("Cus_name"),
                        rs.getString("Cus_mobile"),
                        rs.getString("Date_time")
                );
                appointments.add(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public String saveAppointment(AppointmentDTO appointment) {
        try {
            var ps = connection.prepareStatement("INSERT INTO appointment (App_id,Adm_id,Cus_name,Cus_mobile,Date_time) VALUES(?,?,?,?,?)");
            ps.setString(1, appointment.getAppId());
            ps.setString(2, appointment.getAdmId());
            ps.setString(3, appointment.getCusName());
            ps.setString(4, appointment.getCusMobile());
            ps.setString(5, appointment.getDateTime());

            if (ps.executeUpdate() != 0) {
                return "Appointment saved successful";
            } else {
                return "Failed to saved appointment";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateAppointment(String appId, AppointmentDTO appointment) {
        try {
            var ps = connection.prepareStatement("UPDATE appointment SET Adm_id=?,Cus_name=?,Cus_mobile=?,Date_time=? WHERE App_id=?");
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
    public AppointmentDTO searchAppointment(String appId) {
        try {
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            var ps = connection.prepareStatement("SELECT * FROM appointment WHERE App_id=?");
            ps.setString(1, appId);
            var rst = ps.executeQuery();

            while (rst.next()) {
                appointmentDTO.setAppId(rst.getString("App_id"));
                appointmentDTO.setAdmId(rst.getString("Adm_id"));
                appointmentDTO.setCusName(rst.getString("Cus_name"));
                appointmentDTO.setCusMobile(rst.getString("Cus_mobile"));
                appointmentDTO.setDateTime(rst.getString("Date_time"));
            }
            return appointmentDTO;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteAppointment(String appId) {
        try {
            var ps = connection.prepareStatement("DELETE FROM appointment WHERE App_id=?");
            ps.setString(1, appId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
