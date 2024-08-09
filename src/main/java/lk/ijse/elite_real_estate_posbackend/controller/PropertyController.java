package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.BOFactory;
import lk.ijse.elite_real_estate_posbackend.bo.custom.PropertyBO;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;
import lk.ijse.elite_real_estate_posbackend.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/property/*", loadOnStartup = 1)
public class PropertyController extends HttpServlet {
    private final PropertyBO propertyBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.PROPERTY);
    static Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Invalid Content Type");
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            PropertyDTO property = jsonb.fromJson(req.getReader(), PropertyDTO.class);
            writer.write(propertyBO.saveProperty(property));
            resp.setStatus(HttpServletResponse.SC_CREATED);
            logger.info("Property Added Successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Add Property");
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
                logger.error("Property ID is missing");
                return;
            }

            String propertyId = pathInfo.substring(1);
            if (propertyId.isEmpty()) {
                writer.write("Property ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Property ID is missing");
                return;
            }

            Jsonb jsonb = JsonbBuilder.create();
            PropertyDTO property = jsonb.fromJson(req.getReader(), PropertyDTO.class);

            if (propertyBO.updateProperty(propertyId, property)) {
                writer.write("Property update successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info("Property Updated Successfully");
            } else {
                writer.write("Property update failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Failed to Update Property");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Update Property");
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
                var property = propertyBO.searchProperty(propertyId);
                if (property != null) {
                    writer.write(jsonb.toJson(property));
                    resp.setStatus(HttpServletResponse.SC_OK);
                    logger.info("Property Retrieved Successfully");
                } else {
                    writer.write("{\"error\": \"Property not found\"}");
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    logger.error("Property not found");
                }
            } else {
                List<PropertyDTO> properties = propertyBO.getAllProperties();
                List<String> supplierIds = propertyBO.getSupplierIds();
                Map<String, Object> result = new HashMap<>();
                result.put("properties", properties);
                result.put("supplierIds", supplierIds);
                writer.write(jsonb.toJson(result));
                resp.setStatus(HttpServletResponse.SC_OK);
                logger.info("All Properties Retrieved Successfully");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Retrieve Properties");
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                writer.write("Property ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Property ID is missing");
                return;
            }

            String[] split = pathInfo.split("/");
            if (split.length != 2) {
                writer.write("Invalid property ID");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Invalid property ID");
                return;
            }

            String propertyId = split[1];

            if (propertyBO.deleteProperty(propertyId)) {
                writer.write("Property deleted successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info("Property Deleted Successfully");
            } else {
                writer.write("Property deleted failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Failed to Delete Property");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Delete Property");
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        logger.info("Property Controller Destroyed");
        super.destroy();
    }
}
