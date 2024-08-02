package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.SupplierDTO;

import java.sql.Connection;
import java.sql.SQLException;

public final class SupplierDAOIMPL implements SupplierDAO {
    @Override
    public String saveSupplier(SupplierDTO supplier, Connection connection) {
        try {
            var ps = connection.prepareStatement("INSERT INTO supplier (Sup_id,Adm_id,Name,Address,Mobile,Email) VALUES(?,?,?,?,?,?)");
            ps.setString(1, supplier.getSupId());
            ps.setString(2, supplier.getAdmId());
            ps.setString(3, supplier.getName());
            ps.setString(4, supplier.getAddress());
            ps.setString(5, supplier.getMobile());
            ps.setString(6, supplier.getEmail());

            if (ps.executeUpdate() != 0) {
                return "Supplier saved successful";
            } else {
                return "Failed to saved supplier";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateSupplier(String supplierId, SupplierDTO supplier, Connection connection) {
        try {
            var ps = connection.prepareStatement("UPDATE supplier SET Adm_id=?,Name=?,Address=?,Mobile=?,Email=? WHERE Sup_id=?");
            ps.setString(1, supplier.getAdmId());
            ps.setString(2, supplier.getName());
            ps.setString(3, supplier.getAddress());
            ps.setString(4, supplier.getMobile());
            ps.setString(5, supplier.getEmail());
            ps.setString(6, supplierId);

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
