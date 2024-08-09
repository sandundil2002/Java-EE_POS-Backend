package lk.ijse.elite_real_estate_posbackend.dao.custom.impl;

import lk.ijse.elite_real_estate_posbackend.dao.custom.SupplierDAO;
import lk.ijse.elite_real_estate_posbackend.dto.SupplierDTO;
import lk.ijse.elite_real_estate_posbackend.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOIMPL implements SupplierDAO {
    private final Connection connection = ConnectionUtil.getInstance().getConnection();

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        List<SupplierDTO> supplierDTOS = new ArrayList<>();
        try {
            var pst = connection.prepareStatement("SELECT * FROM supplier");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                SupplierDTO supplierDTO = new SupplierDTO();
                supplierDTO.setSupId(rs.getString("Sup_id"));
                supplierDTO.setAdmId(rs.getString("Adm_id"));
                supplierDTO.setName(rs.getString("Name"));
                supplierDTO.setAddress(rs.getString("Address"));
                supplierDTO.setMobile(rs.getString("Mobile"));
                supplierDTO.setEmail(rs.getString("Email"));
                supplierDTOS.add(supplierDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplierDTOS;
    }

    @Override
    public String saveSupplier(SupplierDTO supplier) {
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
    public boolean updateSupplier(String supplierId, SupplierDTO supplier) {
        try {
            var ps = connection.prepareStatement("UPDATE supplier SET Adm_id=?,Name=?,Address=?,Mobile=?,Email=? WHERE Sup_id=?");
            ps.setString(1, supplier.getAdmId());
            ps.setString(2, supplier.getName());
            ps.setString(3, supplier.getAddress());
            ps.setString(4, supplier.getMobile());
            ps.setString(5, supplier.getEmail());
            ps.setString(6, supplier.getSupId());

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public SupplierDTO searchSupplier(String supplierId) {
        try {
            SupplierDTO supplierDTO = new SupplierDTO();
            var ps = connection.prepareStatement("SELECT * FROM supplier WHERE Sup_id=?");
            ps.setString(1, supplierId);
            var rst = ps.executeQuery();

            while (rst.next()) {
                supplierDTO.setSupId(rst.getString("Sup_id"));
                supplierDTO.setAdmId(rst.getString("Adm_id"));
                supplierDTO.setName(rst.getString("Name"));
                supplierDTO.setAddress(rst.getString("Address"));
                supplierDTO.setMobile(rst.getString("Mobile"));
                supplierDTO.setEmail(rst.getString("Email"));
            }
            return supplierDTO;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteSupplier(String supplierId) {
        try {
            var ps = connection.prepareStatement("DELETE FROM supplier WHERE Sup_id=?");
            ps.setString(1, supplierId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> getAdminIds() {
        List<String> adminIds = new ArrayList<>();
        try {
            var pst = connection.prepareStatement("SELECT Adm_id FROM admin");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                adminIds.add(rs.getString("Adm_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminIds;
    }
}
