package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;
import lk.ijse.elite_real_estate_posbackend.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class PaymentDAOIMPL implements PaymentDAO {
    private final Connection connection = ConnectionUtil.getInstance().getConnection();

    @Override
    public String savePayment(PaymentDTO payment) {
        try {
            var ps = connection.prepareStatement("INSERT INTO payment (Pay_id,Pro_id,Cus_id,Pro_price,Cus_name,Method) VALUES(?,?,?,?,?,?)");
            ps.setString(1, payment.getPayId());
            ps.setString(2, payment.getProId());
            ps.setString(3, payment.getCusId());
            ps.setString(4, payment.getProPrice());
            ps.setString(5, payment.getCusName());
            ps.setString(6, payment.getMethod());

            if (ps.executeUpdate() != 0) {
                return "Payment saved successful";
            } else {
                return "Failed to saved payment";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
            var ps = connection.prepareStatement("SELECT Pro_id,Price FROM property");
            var rs = ps.executeQuery();
            while (rs.next()) {
                properties.add(new PropertyDTO(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        return customers;
    }
}
