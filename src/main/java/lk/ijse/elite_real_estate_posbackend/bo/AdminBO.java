package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dto.AdminDTO;

public sealed interface AdminBO permits AdminBOIMPL{
    String saveAdmin(AdminDTO admin);
}
