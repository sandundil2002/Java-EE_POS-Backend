package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.CustomerBOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;
import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/customer/*", loadOnStartup = 1)
public class CustomerController extends HttpServlet {
    private final CustomerBOIMPL customerBOIMPL = new CustomerBOIMPL();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            writer.write(customerBOIMPL.saveCustomer(customer));
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

            if (customerBOIMPL.updateCustomer(customerId, customer)) {
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
        String customerId = req.getParameter("cusId");

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();

            if (customerId != null) {
                var customer = customerBOIMPL.searchCustomer(customerId);
                if (customer != null) {
                    writer.write(jsonb.toJson(customer));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    writer.write("Customer not found");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                List<CustomerDTO> customers = customerBOIMPL.getAllCustomers();
                List<String> appointmentIds = customerBOIMPL.getAppointmentIds();
                Map<String, Object> result = new HashMap<>();
                result.put("customers", customers);
                result.put("appointments", appointmentIds);
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
            var customerId = req.getParameter("cusId");
            if (customerBOIMPL.deleteCustomer(customerId)) {
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

    @Override
    public void destroy() {
        super.destroy();
    }
}
