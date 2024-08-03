package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;

public sealed interface PaymentDAO permits PaymentDAOIMPL {
    String savePayment(PaymentDTO payment);
}
