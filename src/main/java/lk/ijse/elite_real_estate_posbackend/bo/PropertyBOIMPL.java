package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dao.PropertyDAOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;

public final class PropertyBOIMPL implements PropertyBO {
    private final PropertyDAOIMPL propertyDAO = new PropertyDAOIMPL();

    @Override
    public String saveProperty(PropertyDTO property) {
        return propertyDAO.saveProperty(property);
    }

    @Override
    public boolean updateProperty(String propertyId, PropertyDTO property) {
        return propertyDAO.updateProperty(propertyId, property);
    }

    @Override
    public PropertyDTO searchProperty(String propertyId) {
        return propertyDAO.searchProperty(propertyId);
    }

    @Override
    public boolean deleteProperty(String propertyId) {
        return propertyDAO.deleteProperty(propertyId);
    }
}
