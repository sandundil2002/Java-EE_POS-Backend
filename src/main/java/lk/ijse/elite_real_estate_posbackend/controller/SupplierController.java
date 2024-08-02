package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.SupplierBOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.SupplierDTO;

import java.io.IOException;

@WebServlet(urlPatterns = "/supplier", loadOnStartup = 1)
public class SupplierController extends HttpServlet {
    private final SupplierBOIMPL supplierBOImpl = new SupplierBOIMPL();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            SupplierDTO supplier = jsonb.fromJson(req.getReader(), SupplierDTO.class);
            writer.write(supplierBOImpl.saveSupplier(supplier));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try (var write = resp.getWriter()) {
            var supplierId = req.getParameter("supId");
            Jsonb jsonb = JsonbBuilder.create();
            SupplierDTO supplier = jsonb.fromJson(req.getReader(), SupplierDTO.class);

            if (supplierBOImpl.updateSupplier(supplierId, supplier)) {
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
        try (var writer = resp.getWriter()) {
            var supplierId = req.getParameter("supId");

            var supplier = supplierBOImpl.searchSupplier(supplierId);
            if (supplier != null) {
                Jsonb jsonb = JsonbBuilder.create();
                writer.write(jsonb.toJson(supplier));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                writer.write("Supplier not found");
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            var supplierId = req.getParameter("supId");

            if (supplierBOImpl.deleteSupplier(supplierId)) {
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
