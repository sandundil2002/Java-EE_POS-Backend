package lk.ijse.elite_real_estate_posbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private String cusId;
    private String appId;
    private String cusName;
    private String address;
    private String mobile;
    private String email;
}
