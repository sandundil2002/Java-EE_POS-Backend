package lk.ijse.elite_real_estate_posbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDTO {
    private String supId;
    private String admId;
    private String supName;
    private String address;
    private String mobile;
    private String email;
}
