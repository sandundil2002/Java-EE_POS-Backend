package lk.ijse.elite_real_estate_posbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private String payId;
    private String proId;
    private String cusId;
    private String price;
    private String cusName;
    private String method;
}
