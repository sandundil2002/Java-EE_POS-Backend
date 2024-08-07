package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dto.SupplierDTO;

import java.sql.Connection;
import java.util.List;

public sealed interface SupplierDAO permits SupplierDAOIMPL{
    List<SupplierDTO> getAllSuppliers();
    String saveSupplier(SupplierDTO supplier);
    boolean updateSupplier(String supplierId, SupplierDTO supplier);
    SupplierDTO searchSupplier(String supplierId);
    boolean deleteSupplier(String supplierId );
    List<String> getAdminIds();
}
