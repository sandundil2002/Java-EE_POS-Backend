package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;

import java.sql.Connection;

public sealed interface CustomerDAO permits CustomerDAOIMPL {
    String saveCustomer(CustomerDTO customer, Connection connection);
    boolean updateCustomer(String cusId, CustomerDTO customer, Connection connection);
    CustomerDTO searchCustomer(String cusId, Connection connection);
    boolean deleteCustomer(String cusId, Connection connection);
}
