package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;

import java.util.List;

public sealed interface PaymentDAO permits PaymentDAOIMPL {
    String savePayment(PaymentDTO payment);
    String generatePaymentID();
    List<PropertyDTO> getAllProperties();
    List<CustomerDTO> getAllCustomers();
    boolean updatePropertyStatus(String proId);
    boolean updateAppointmentStatus(String cusId);
}
