package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dao.CustomerDAOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;

import java.sql.Connection;

public final class CustomerBOIMPL implements CustomerBO {
    private final CustomerDAOIMPL customerDAO = new CustomerDAOIMPL();

    @Override
    public String saveCustomer(CustomerDTO customer, Connection connection) {
        return customerDAO.saveCustomer(customer, connection);
    }

    @Override
    public boolean updateCustomer(String cusId, CustomerDTO customer, Connection connection) {
        return customerDAO.updateCustomer(cusId, customer, connection);
    }

    @Override
    public CustomerDTO searchCustomer(String cusId, Connection connection) {
        return customerDAO.searchCustomer(cusId, connection);
    }

    @Override
    public boolean deleteCustomer(String cusId, Connection connection) {
        return customerDAO.deleteCustomer(cusId, connection);
    }
}
