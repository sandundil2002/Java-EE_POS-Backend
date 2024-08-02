package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.PropertyBOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;

import java.io.IOException;

@WebServlet(urlPatterns = "/property", loadOnStartup = 1)
public class PropertyController extends HttpServlet {
    private final PropertyBOIMPL propertyBOImpl = new PropertyBOIMPL();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            PropertyDTO property = jsonb.fromJson(req.getReader(), PropertyDTO.class);
            writer.write(propertyBOImpl.saveProperty(property));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
