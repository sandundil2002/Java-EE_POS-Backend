package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;

import java.util.List;

public interface CustomerBO {
    List<CustomerDTO> getAllCustomers();
    String saveCustomer(CustomerDTO customer);
    boolean updateCustomer(String cusId, CustomerDTO customer);
    CustomerDTO searchCustomer(String cusId);
    boolean deleteCustomer(String cusId);
    List<String> getAppointmentIds();
}
