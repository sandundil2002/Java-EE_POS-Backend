package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.CustomerBOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer", loadOnStartup = 1)
public class CustomerController extends HttpServlet {
    private final CustomerBOIMPL customerBOIMPL = new CustomerBOIMPL();
    Connection connection;

    @Override
    public void init() {
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/elite_real_estate_pos");
            this.connection = pool.getConnection();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            writer.write(customerBOIMPL.saveCustomer(customer, connection));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try (var write = resp.getWriter()) {
            var customerId = req.getParameter("cusId");
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            if (customerBOIMPL.updateCustomer(customerId, customer, connection)) {
                write.write("Customer update successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                write.write("Customer update failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            var customerId = req.getParameter("cusId");
            CustomerDTO customer = customerBOIMPL.searchCustomer(customerId, connection);

            if (customer != null) {
                Jsonb jsonb = JsonbBuilder.create();
                writer.write(jsonb.toJson(customer));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            var customerId = req.getParameter("cusId");
            if (customerBOIMPL.deleteCustomer(customerId, connection)) {
                writer.write("Customer Delete successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                writer.write("Customer Delete failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
