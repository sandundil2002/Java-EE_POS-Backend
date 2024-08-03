package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;
import lk.ijse.elite_real_estate_posbackend.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

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
}
