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
    private String name;
    private String address;
    private String mobile;
    private String email;

    public CustomerDTO(String cusId, String name) {
        this.cusId = cusId;
        this.name = name;
    }
}
