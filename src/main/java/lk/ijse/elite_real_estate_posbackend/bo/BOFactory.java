package lk.ijse.elite_real_estate_posbackend.bo;

import lk.ijse.elite_real_estate_posbackend.bo.custom.impl.AdminBOIMPL;
import lk.ijse.elite_real_estate_posbackend.bo.custom.impl.AppointmentBOIMPL;
import lk.ijse.elite_real_estate_posbackend.bo.custom.impl.CustomerBOIMPL;
import lk.ijse.elite_real_estate_posbackend.bo.custom.impl.PropertyBOIMPL;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return (boFactory == null) ? (boFactory = new BOFactory()) : (boFactory);
    }

    public enum BOTypes {
        PROPERTY, APPOINTMENT, CUSTOMER, ADMIN, PAYMENT, SUPPLIER
    }

    public <T> T getBO(BOTypes boType) {
        switch (boType) {
            case PROPERTY:
                return (T) new PropertyBOIMPL();
            case APPOINTMENT:
                return (T) new AppointmentBOIMPL();
            case CUSTOMER:
                return (T) new CustomerBOIMPL();
            case ADMIN:
                return (T) new AdminBOIMPL();
            default:
                return null;
        }
    }
}
