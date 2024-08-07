package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.AdminDTO;
import lk.ijse.elite_real_estate_posbackend.util.ConnectionUtil;

import java.sql.Connection;

public final class AdminDAOIMPL implements AdminDAO {
    private final Connection connection = ConnectionUtil.getInstance().getConnection();

    @Override
    public String saveAdmin(AdminDTO admin) {
        try {
            var ps = connection.prepareStatement("INSERT INTO admin (Adm_id,name,Address,Mobile,Email,Password) VALUES(?,?,?,?,?,?)");
            ps.setString(1, admin.getAdmId());
            ps.setString(2, admin.getName());
            ps.setString(3, admin.getAddress());
            ps.setString(4, admin.getMobile());
            ps.setString(5, admin.getEmail());
            ps.setString(6, admin.getPassword());

            if (ps.executeUpdate() != 0) {
                return "Admin saved successful";
            } else {
                return "Failed to saved admin";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to saved admin";
        }
    }
}
