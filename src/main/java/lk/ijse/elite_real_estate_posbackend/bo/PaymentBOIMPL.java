package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dao.PaymentDAO;
import lk.ijse.elite_real_estate_posbackend.dao.PaymentDAOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;

import java.util.List;

public final class PaymentBOIMPL implements PaymentBO {
    private final PaymentDAO paymentDAO = new PaymentDAOIMPL();

    @Override
    public String savePayment(PaymentDTO payment) {
        return paymentDAO.savePayment(payment);
    }

    @Override
    public String generatePaymentID() {
        return paymentDAO.generatePaymentID();
    }

    @Override
    public List<PropertyDTO> getAllProperties() {
        return paymentDAO.getAllProperties();
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return paymentDAO.getAllCustomers();
    }
}
