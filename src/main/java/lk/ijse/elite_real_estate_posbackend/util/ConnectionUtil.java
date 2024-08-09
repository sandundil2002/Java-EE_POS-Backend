package lk.ijse.elite_real_estate_posbackend.util;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Data
public class ConnectionUtil {
    static Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);
    private static ConnectionUtil instance;
    private DataSource dataSource;
    private Connection connection;

    private ConnectionUtil() {
        try {
            var initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/elite_real_estate_pos");
            this.connection = dataSource.getConnection();
            logger.info("Connection Initialized", this.connection);
        } catch (SQLException | NamingException e) {
            logger.error("Connection Initialize Error", this.connection);
            e.printStackTrace();
        }
    }

    public static ConnectionUtil getInstance() {
        if (instance == null) {
            instance = new ConnectionUtil();
        }
        logger.info("Connection Util Instance Created", instance);
        return instance;
    }
}
