package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/appointment", loadOnStartup = 1)
public class AppointmentController extends HttpServlet {
    Connection connection;
    @Override
    public void init() {
        System.out.println("Init");
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/elite_real_estate_pos");
            this.connection = pool.getConnection();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }
}
