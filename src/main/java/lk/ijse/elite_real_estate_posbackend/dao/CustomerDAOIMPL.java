package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public final class CustomerDAOIMPL implements CustomerDAO {
    @Override
    public String saveCustomer(CustomerDTO customer, Connection connection) {
        try {
            var ps = connection.prepareStatement("INSERT INTO customer (Cus_id,App_id,Name,Address,Mobile,Email) VALUES(?,?,?,?,?,?)");
            ps.setString(1, customer.getCusId());
            ps.setString(2, customer.getAppId());
            ps.setString(3, customer.getName());
            ps.setString(4, customer.getAddress());
            ps.setString(5, customer.getMobile());
            ps.setString(6, customer.getEmail());

            if (ps.executeUpdate() != 0) {
                return "Customer saved successful";
            } else {
                return "Failed to saved customer";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateCustomer(String cusId, CustomerDTO customer, Connection connection) {
        try {
            var ps = connection.prepareStatement("UPDATE customer SET App_id=?,Name=?,Address=?,Mobile=?,Email=? WHERE Cus_id=?");
            ps.setString(1, customer.getAppId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getMobile());
            ps.setString(5, customer.getEmail());
            ps.setString(6,cusId);

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CustomerDTO searchCustomer(String cusId, Connection connection) {
        try {
            CustomerDTO customerDTO = new CustomerDTO();
            var ps = connection.prepareStatement("SELECT * FROM customer WHERE Cus_id=?");
            ps.setString(1, cusId);
            var rst = ps.executeQuery();

            while (rst.next()){
                customerDTO.setCusId(rst.getString("Cus_id"));
                customerDTO.setAppId(rst.getString("App_id"));
                customerDTO.setName(rst.getString("Name"));
                customerDTO.setAddress(rst.getString("Address"));
                customerDTO.setMobile(rst.getString("Mobile"));
                customerDTO.setEmail(rst.getString("Email"));
            }
            return customerDTO;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}