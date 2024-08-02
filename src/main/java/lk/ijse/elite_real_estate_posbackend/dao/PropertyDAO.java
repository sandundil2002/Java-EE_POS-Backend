package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;

public sealed interface PropertyDAO permits PropertyDAOIMPL {
    String saveProperty(PropertyDTO property);
}
