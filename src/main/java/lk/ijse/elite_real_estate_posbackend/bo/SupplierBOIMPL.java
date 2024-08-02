package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dao.SupplierDAOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.SupplierDTO;

import java.sql.Connection;

public final class SupplierBOIMPL implements SupplierBO {
    private final SupplierDAOIMPL supplierDAO = new SupplierDAOIMPL();

    @Override
    public String saveSupplier(SupplierDTO supplier, Connection connection) {
        return supplierDAO.saveSupplier(supplier, connection);
    }

    @Override
    public boolean updateSupplier(String supplierId, SupplierDTO supplier, Connection connection) {
        return supplierDAO.updateSupplier(supplierId, supplier, connection);
    }

    @Override
    public SupplierDTO searchSupplier(String supplierId, Connection connection) {
        return supplierDAO.searchSupplier(supplierId, connection);
    }

    @Override
    public boolean deleteSupplier(String supplierId, Connection connection) {
        return supplierDAO.deleteSupplier(supplierId, connection);
    }
}
