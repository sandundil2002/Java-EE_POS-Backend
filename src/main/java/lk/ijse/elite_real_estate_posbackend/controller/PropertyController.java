package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.PropertyBOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/property/*", loadOnStartup = 1)
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                writer.write("Property ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String propertyId = pathInfo.substring(1);
            if (propertyId.isEmpty()) {
                writer.write("Property ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Jsonb jsonb = JsonbBuilder.create();
            PropertyDTO property = jsonb.fromJson(req.getReader(), PropertyDTO.class);

            if (propertyBOImpl.updateProperty(propertyId, property)) {
                writer.write("Property update successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                writer.write("Property update failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        var propertyId = req.getParameter("proId");

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");
            if (propertyId != null) {
                var property = propertyBOImpl.searchProperty(propertyId);
                if (property != null) {
                    writer.write(jsonb.toJson(property));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    writer.write("{\"error\": \"Property not found\"}");
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                List<PropertyDTO> properties = propertyBOImpl.getAllProperties();
                List<String> supplierIds = propertyBOImpl.getSupplierIds();
                Map<String, Object> result = new HashMap<>();
                result.put("properties", properties);
                result.put("supplierIds", supplierIds);
                writer.write(jsonb.toJson(result));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                writer.write("Property ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] split = pathInfo.split("/");
            if (split.length != 2) {
                writer.write("Invalid property ID");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String propertyId = split[1];

            if (propertyBOImpl.deleteProperty(propertyId)) {
                writer.write("Property deleted successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                writer.write("Property deleted failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
