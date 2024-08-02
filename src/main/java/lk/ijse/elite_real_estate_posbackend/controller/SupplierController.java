package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.SupplierBOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.SupplierDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/supplier", loadOnStartup = 1)
public class SupplierController extends HttpServlet {
    private final SupplierBOIMPL supplierBOImpl = new SupplierBOIMPL();
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
            SupplierDTO supplier = jsonb.fromJson(req.getReader(), SupplierDTO.class);
            writer.write(supplierBOImpl.saveSupplier(supplier, connection));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
