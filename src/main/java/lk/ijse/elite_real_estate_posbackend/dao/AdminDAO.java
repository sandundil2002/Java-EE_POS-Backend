package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.AdminDTO;

public sealed interface AdminDAO permits AdminDAOIMPL{
    String saveAdmin(AdminDTO admin);
}
