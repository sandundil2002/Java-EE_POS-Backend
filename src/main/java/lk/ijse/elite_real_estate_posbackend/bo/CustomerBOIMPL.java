package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dao.CustomerDAOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;

import java.util.List;

public final class CustomerBOIMPL implements CustomerBO {
    private final CustomerDAOIMPL customerDAO = new CustomerDAOIMPL();

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    public String saveCustomer(CustomerDTO customer) {
        return customerDAO.saveCustomer(customer);
    }

    @Override
    public boolean updateCustomer(String cusId, CustomerDTO customer) {
        return customerDAO.updateCustomer(cusId, customer);
    }

    @Override
    public CustomerDTO searchCustomer(String cusId ) {
        return customerDAO.searchCustomer(cusId);
    }

    @Override
    public boolean deleteCustomer(String cusId ) {
        return customerDAO.deleteCustomer(cusId);
    }

    @Override
    public List<String> getAppointmentIds() {
        return customerDAO.getAppointmentIds();
    }
}
