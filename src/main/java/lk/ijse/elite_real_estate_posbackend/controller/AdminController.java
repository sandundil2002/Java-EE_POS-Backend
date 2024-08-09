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

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/*", loadOnStartup = 1)
public class AdminController extends HttpServlet {
    private final AdminBO adminBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.ADMIN);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            AdminDTO admin = jsonb.fromJson(req.getReader(), AdminDTO.class);
            writer.write(adminBO.saveAdmin(admin));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
