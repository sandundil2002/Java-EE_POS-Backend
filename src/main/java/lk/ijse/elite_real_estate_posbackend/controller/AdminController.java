package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.custom.AdminBO;
import lk.ijse.elite_real_estate_posbackend.bo.BOFactory;
import lk.ijse.elite_real_estate_posbackend.dto.AdminDTO;
import lk.ijse.elite_real_estate_posbackend.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/*", loadOnStartup = 1)
public class AdminController extends HttpServlet {
    private final AdminBO adminBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.ADMIN);
    static Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Invalid Content Type");
        }

        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            AdminDTO admin = jsonb.fromJson(req.getReader(), AdminDTO.class);
            writer.write(adminBO.saveAdmin(admin));
            resp.setStatus(HttpServletResponse.SC_CREATED);
            logger.info("Admin Added Successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Add Admin");
            e.printStackTrace();
        }
    }
}
