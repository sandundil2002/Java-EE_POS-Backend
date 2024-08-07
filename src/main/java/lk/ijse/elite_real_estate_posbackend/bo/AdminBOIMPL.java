package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dao.AdminDAOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.AdminDTO;

public final class AdminBOIMPL implements AdminBO {
    private final AdminDAOIMPL adminDAO = new AdminDAOIMPL();

    @Override
    public String saveAdmin(AdminDTO admin) {
        return adminDAO.saveAdmin(admin);
    }
}
