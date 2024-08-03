package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dao.PaymentDAO;
import lk.ijse.elite_real_estate_posbackend.dao.PaymentDAOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;

public final class PaymentBOIMPL implements PaymentBO {
    private final PaymentDAO paymentDAO = new PaymentDAOIMPL();

    @Override
    public String savePayment(PaymentDTO payment) {
        return paymentDAO.savePayment(payment);
    }
}
