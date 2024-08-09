package lk.ijse.elite_real_estate_posbackend.dao.custom.impl;

import lk.ijse.elite_real_estate_posbackend.dao.custom.PaymentDAO;
import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;
import lk.ijse.elite_real_estate_posbackend.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOIMPL implements PaymentDAO {
    private final Connection connection = ConnectionUtil.getInstance().getConnection();

    @Override
    public String savePayment(PaymentDTO payment) {
        try {
            connection.setAutoCommit(false);

            var ps = connection.prepareStatement("INSERT INTO payment (Pay_id,Pro_id,Cus_id,Pro_price,Cus_name,Method) VALUES(?,?,?,?,?,?)");
            ps.setString(1, payment.getPayId());
            ps.setString(2, payment.getProId());
            ps.setString(3, payment.getCusId());
            ps.setString(4, payment.getProPrice());
            ps.setString(5, payment.getCusName());
            ps.setString(6, payment.getMethod());

            if (ps.executeUpdate() == 0) {
                connection.rollback();
                return "Failed to save payment";
            }

            if (!updatePropertyStatus(payment.getProId())) {
                connection.rollback();
                return "Failed to update property status";
            }

            if (!updateAppointmentStatus(payment.getCusId())) {
                connection.rollback();
                return "Failed to update appointment status";
            }

            connection.commit();
            return "Payment saved successfully";
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
            return "Error occurred while saving payment";
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String generatePaymentID() {
        try {
            var ps = connection.prepareStatement("SELECT Pay_id FROM payment ORDER BY Pay_id DESC LIMIT 1");
            var rs = ps.executeQuery();
            if (rs.next()) {
                String lastId = rs.getString(1);
                int newId = Integer.parseInt(lastId.substring(1)) + 1;
                if (newId < 10) {
                    return "O00" + newId;
                } else if (newId < 100) {
                    return "O0" + newId;
                } else {
                    return "O" + newId;
                }
            } else {
                return "O001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "O001";
        }
    }

    @Override
    public List<PropertyDTO> getAllProperties() {
        List<PropertyDTO> properties = new ArrayList<>();
        try {
            var ps = connection.prepareStatement("SELECT * FROM property");
            var rs = ps.executeQuery();
            while (rs.next()) {
                properties.add(new PropertyDTO(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("properties = " + properties);
        return properties;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customers = new ArrayList<>();
        try {
            var ps = connection.prepareStatement("SELECT Cus_id,Name FROM customer");
            var rs = ps.executeQuery();
            while (rs.next()) {
                customers.add(new CustomerDTO(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("customers = " + customers);
        return customers;
    }

    @Override
    public boolean updatePropertyStatus(String proId) {
        try {
            var ps = connection.prepareStatement("UPDATE property SET Status = 'Sold' WHERE Pro_id = ?");
            ps.setString(1, proId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAppointmentStatus(String cusId) {
        try {
            var selectPs = connection.prepareStatement("SELECT App_id FROM customer WHERE Cus_id = ?");
            selectPs.setString(1, cusId);
            var rs = selectPs.executeQuery();

            boolean updateSuccessful = false;

            while (rs.next()) {
                String appId = rs.getString("App_id");
                var updatePs = connection.prepareStatement("UPDATE appointment SET Status = 'Completed' WHERE App_id = ?");
                updatePs.setString(1, appId);
                updateSuccessful = updatePs.executeUpdate() != 0;
            }
            return updateSuccessful;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
