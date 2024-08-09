package lk.ijse.elite_real_estate_posbackend.dao.custom;

import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;

import java.util.List;

public interface CustomerDAO {
    List<CustomerDTO> getAllCustomers();
    String saveCustomer(CustomerDTO customer);
    boolean updateCustomer(String cusId, CustomerDTO customer);
    CustomerDTO searchCustomer(String cusId);
    boolean deleteCustomer(String cusId);
    List<String> getAppointmentIds();
}
