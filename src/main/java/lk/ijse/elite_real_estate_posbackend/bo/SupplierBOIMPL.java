package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.dao.DAOFactory;
import lk.ijse.elite_real_estate_posbackend.dao.custom.SupplierDAO;
import lk.ijse.elite_real_estate_posbackend.dto.SupplierDTO;

import java.util.List;

public class SupplierBOIMPL implements SupplierBO {
    private final SupplierDAO supplierDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }

    @Override
    public String saveSupplier(SupplierDTO supplier) {
        return supplierDAO.saveSupplier(supplier);
    }

    @Override
    public boolean updateSupplier(String supplierId, SupplierDTO supplier) {
        return supplierDAO.updateSupplier(supplierId, supplier);
    }

    @Override
    public SupplierDTO searchSupplier(String supplierId) {
        return supplierDAO.searchSupplier(supplierId);
    }

    @Override
    public boolean deleteSupplier(String supplierId ) {
        return supplierDAO.deleteSupplier(supplierId);
    }

    @Override
    public List<String> getAdminIds() {
        return supplierDAO.getAdminIds();
    }
}
