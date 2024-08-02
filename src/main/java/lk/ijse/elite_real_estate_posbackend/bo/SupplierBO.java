package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dto.SupplierDTO;

import java.sql.Connection;

public sealed interface SupplierBO permits SupplierBOIMPL{
    String saveSupplier(SupplierDTO supplier, Connection connection);
    boolean updateSupplier(String supplierId, SupplierDTO supplier, Connection connection);
}
