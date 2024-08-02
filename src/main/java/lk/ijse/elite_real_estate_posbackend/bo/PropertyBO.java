package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;

public sealed interface PropertyBO permits PropertyBOIMPL {
    String saveProperty(PropertyDTO property);
}
