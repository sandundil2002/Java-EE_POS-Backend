package lk.ijse.elite_real_estate_posbackend.dao.custom.impl;

import lk.ijse.elite_real_estate_posbackend.dao.custom.PropertyDAO;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;
import lk.ijse.elite_real_estate_posbackend.util.ConnectionUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAOIMPL implements PropertyDAO {
    private final Connection connection = ConnectionUtil.getInstance().getConnection();

    @Override
    public List<PropertyDTO> getAllProperties() {
        List<PropertyDTO> propertyDTOS = new ArrayList<>();
        try {
            var pst = connection.prepareStatement("SELECT * FROM property");
            var rs = pst.executeQuery();

            while (rs.next()) {
                PropertyDTO propertyDTO = new PropertyDTO();
                propertyDTO.setProId(rs.getString("Pro_id"));
                propertyDTO.setSupId(rs.getString("Sup_id"));
                propertyDTO.setType(rs.getString("Type"));
                propertyDTO.setAddress(rs.getString("Address"));
                propertyDTO.setPrice(rs.getString("Price"));
                propertyDTO.setPerches(rs.getString("Perches"));
                propertyDTO.setStatus(rs.getString("Status"));
                propertyDTOS.add(propertyDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return propertyDTOS;
    }

    @Override
    public String saveProperty(PropertyDTO property) {
        try {
            var ps = connection.prepareStatement("INSERT INTO property (Pro_id,Sup_id,Type,Address,Price,Perches,Status) VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, property.getProId());
            ps.setString(2, property.getSupId());
            ps.setString(3, property.getType());
            ps.setString(4, property.getAddress());
            ps.setString(5, property.getPrice());
            ps.setString(6, property.getPerches());
            ps.setString(7, property.getStatus());

            if (ps.executeUpdate() != 0) {
                return "Property saved successful";
            } else {
                return "Failed to saved property";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateProperty(String propertyId, PropertyDTO property) {
        try {
            var ps = connection.prepareStatement("UPDATE property SET Sup_id=?,Type=?,Address=?,Price=?,Perches=?,Status=? WHERE Pro_id=?");
            ps.setString(1, property.getSupId());
            ps.setString(2, property.getType());
            ps.setString(3, property.getAddress());
            ps.setString(4, property.getPrice());
            ps.setString(5, property.getPerches());
            ps.setString(6, property.getStatus());
            ps.setString(7, property.getProId());

            boolean b = ps.executeUpdate() != 0;
            System.out.println(b);
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PropertyDTO searchProperty(String propertyId) {
        try {
            PropertyDTO propertyDTO = new PropertyDTO();
            var ps = connection.prepareStatement("SELECT * FROM property WHERE Pro_id=?");
            ps.setString(1, propertyId);
            var rst = ps.executeQuery();

            while (rst.next()) {
                propertyDTO.setProId(rst.getString("Pro_id"));
                propertyDTO.setSupId(rst.getString("Sup_id"));
                propertyDTO.setType(rst.getString("Type"));
                propertyDTO.setAddress(rst.getString("Address"));
                propertyDTO.setPrice(rst.getString("Price"));
                propertyDTO.setPerches(rst.getString("Perches"));
                propertyDTO.setStatus(rst.getString("Status"));
            }
            return propertyDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteProperty(String propertyId) {
        try {
            var ps = connection.prepareStatement("DELETE FROM property WHERE Pro_id=?");
            ps.setString(1, propertyId);

            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> getSupplierIds() {
        List<String> supplierIds = new ArrayList<>();
        try {
            var ps = connection.prepareStatement("SELECT Sup_id FROM supplier");
            var rst = ps.executeQuery();

            while (rst.next()) {
                supplierIds.add(rst.getString("Sup_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplierIds;
    }
}
