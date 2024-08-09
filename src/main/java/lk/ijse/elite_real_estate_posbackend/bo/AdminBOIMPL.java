package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dao.custom.AdminDAO;

import lk.ijse.elite_real_estate_posbackend.dao.DAOFactory;
import lk.ijse.elite_real_estate_posbackend.dto.AdminDTO;

public class AdminBOIMPL implements AdminBO {
    private final AdminDAO adminDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ADMIN);

    @Override
    public String saveAdmin(AdminDTO admin) {
        return adminDAO.saveAdmin(admin);
    }
}
