package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;

import java.util.List;

public sealed interface PropertyDAO permits PropertyDAOIMPL {
    List<PropertyDTO> getAllProperties();
    String saveProperty(PropertyDTO property);
    boolean updateProperty(String propertyId, PropertyDTO property);
    PropertyDTO searchProperty(String propertyId);
    boolean deleteProperty(String propertyId);
    List<String> getSupplierIds();
}
