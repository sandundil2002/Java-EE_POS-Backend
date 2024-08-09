package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dao.DAOFactory;
import lk.ijse.elite_real_estate_posbackend.dao.custom.PaymentDAO;
import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;

import java.util.List;

public class PaymentBOIMPL implements PaymentBO {
    private final PaymentDAO paymentDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

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

    @Override
    public boolean updatePropertyStatus(String proId) {
        return paymentDAO.updatePropertyStatus(proId);
    }

    @Override
    public boolean updateAppointmentStatus(String cusId) {
        return paymentDAO.updateAppointmentStatus(cusId);
    }
}
