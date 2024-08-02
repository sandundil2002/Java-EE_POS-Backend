package lk.ijse.elite_real_estate_posbackend.util;

import lombok.Data;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Data
public class ConnectionUtil {
    private static ConnectionUtil instance;
    private DataSource dataSource;
    private Connection connection;

    private ConnectionUtil() {
        try {
            var initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/elite_real_estate_pos");
            this.connection = dataSource.getConnection();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionUtil getInstance() {
        if (instance == null) {
            instance = new ConnectionUtil();
        }
        return instance;
    }
}
