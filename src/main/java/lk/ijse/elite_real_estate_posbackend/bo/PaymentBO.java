package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;

import java.util.List;

public sealed interface PaymentBO permits PaymentBOIMPL {
    String savePayment(PaymentDTO payment);
    String generatePaymentID();
    List<PropertyDTO> getAllProperties();
    List<CustomerDTO> getAllCustomers();
}
