package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.SupplierDTO;

import java.sql.Connection;

public sealed interface SupplierDAO permits SupplierDAOIMPL{
    String saveSupplier(SupplierDTO supplier, Connection connection);
    boolean updateSupplier(String supplierId, SupplierDTO supplier, Connection connection);
    SupplierDTO searchSupplier(String supplierId, Connection connection);
    boolean deleteSupplier(String supplierId, Connection connection);
}
