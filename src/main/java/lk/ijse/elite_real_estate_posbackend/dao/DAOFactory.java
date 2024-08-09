package lk.ijse.elite_real_estate_posbackend.dao;

import lk.ijse.elite_real_estate_posbackend.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public enum DAOType {
        ADMIN, APPOINTMENT, Customer, PAYMENT, PROPERTY, SUPPLIER
    }

    public <T> T getDAO(DAOType daoType) {
        switch (daoType) {
            case ADMIN:
                return (T) new AdminDAOIMPL();
            case APPOINTMENT:
                return (T) new AppointmentDAOIMPL();
            case Customer:
                return (T) new CustomerDAOIMPL();
            case PAYMENT:
                return (T) new PaymentDAOIMPL();
            case PROPERTY:
                return (T) new PropertyDAOIMPL();
            case SUPPLIER:
                return (T) new SupplierDAOIMPL();
            default:
                return null;
        }
    }
}
