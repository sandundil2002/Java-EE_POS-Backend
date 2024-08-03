package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;

public sealed interface PaymentBO permits PaymentBOIMPL {
    String savePayment(PaymentDTO payment);
}
