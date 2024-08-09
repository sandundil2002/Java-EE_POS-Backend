package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.BOFactory;
import lk.ijse.elite_real_estate_posbackend.bo.custom.CustomerBO;
import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/customer/*", loadOnStartup = 1)
public class CustomerController extends HttpServlet {
    private final CustomerBO customerBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            writer.write(customerBO.saveCustomer(customer));
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
                write.write("Customer ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String customerId = pathInfo.substring(1);
            if (customerId.isEmpty()) {
                write.write("Customer ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            System.out.println("Received customer: " + customer.toString());

            if (customerBO.updateCustomer(customerId, customer)) {
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
            resp.setContentType("application/json");

            if (customerId != null) {
                var customer = customerBO.searchCustomer(customerId);
                if (customer != null) {
                    writer.write(jsonb.toJson(customer));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    writer.write("{\"error\": \"Customer not found\"}");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                List<CustomerDTO> customers = customerBO.getAllCustomers();
                List<String> appointmentIds = customerBO.getAppointmentIds();
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
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                writer.write("Customer ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String [] split = pathInfo.split("/");
            if (split.length != 2) {
                writer.write("Invalid Customer ID");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String customerId = split[1];

            if (customerBO.deleteCustomer(customerId)) {
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
