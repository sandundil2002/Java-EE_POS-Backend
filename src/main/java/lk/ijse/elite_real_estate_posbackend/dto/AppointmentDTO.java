package lk.ijse.elite_real_estate_posbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDTO {
    private String appId;
    private String admId;
    private String cusName;
    private String cusMobile;
    private String dateTime;
}
