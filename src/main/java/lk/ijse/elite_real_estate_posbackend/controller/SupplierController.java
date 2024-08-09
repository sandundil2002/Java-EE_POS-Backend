package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.BOFactory;
import lk.ijse.elite_real_estate_posbackend.bo.custom.SupplierBO;
import lk.ijse.elite_real_estate_posbackend.dto.SupplierDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/supplier/*", loadOnStartup = 1)
public class  SupplierController extends HttpServlet {
    private final SupplierBO supplierBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.SUPPLIER);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            SupplierDTO supplier = jsonb.fromJson(req.getReader(), SupplierDTO.class);
            writer.write(supplierBO.saveSupplier(supplier));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try (var write = resp.getWriter()) {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                write.write("Supplier ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String supplierId = pathInfo.substring(1);
            if (supplierId.isEmpty()) {
                write.write("Supplier ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Jsonb jsonb = JsonbBuilder.create();
            SupplierDTO supplier = jsonb.fromJson(req.getReader(), SupplierDTO.class);

            if (supplierBO.updateSupplier(supplierId, supplier)) {
                write.write("Supplier updated successfully");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                write.write("Failed to update supplier");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String supplierId = req.getParameter("supId");

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");

            if (supplierId != null) {
                var supplier = supplierBO.searchSupplier(supplierId);
                if (supplier != null) {
                    writer.write(jsonb.toJson(supplier));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    writer.write("{\"error\": \"Supplier not found\"}");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                List<SupplierDTO> suppliers = supplierBO.getAllSuppliers();
                List<String> adminIds = supplierBO.getAdminIds();
                Map<String, Object> result = new HashMap<>();
                result.put("suppliers", suppliers);
                result.put("adminIds", adminIds);
                writer.write(jsonb.toJson(result));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                writer.write("Supplier ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] split = pathInfo.split("/");
            if (split.length != 2) {
                writer.write("Invalid supplier ID");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String supplierId = split[1];

            if (supplierBO.deleteSupplier(supplierId)) {
                writer.write("Supplier deleted successfully");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                writer.write("Failed to delete supplier");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
