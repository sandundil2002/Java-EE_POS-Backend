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
import lk.ijse.elite_real_estate_posbackend.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/customer/*", loadOnStartup = 1)
public class CustomerController extends HttpServlet {
    private final CustomerBO customerBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);
    static Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Invalid Content Type");
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            writer.write(customerBO.saveCustomer(customer));
            resp.setStatus(HttpServletResponse.SC_CREATED);
            logger.info("Customer Added Successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Add Customer");
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
                logger.error("Customer ID is missing");
                return;
            }

            String customerId = pathInfo.substring(1);
            if (customerId.isEmpty()) {
                write.write("Customer ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Customer ID is missing");
                return;
            }

            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            if (customerBO.updateCustomer(customerId, customer)) {
                write.write("Customer update successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info("Customer Updated Successfully");
            } else {
                write.write("Customer update failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Failed to Update Customer");
            }
        } catch (Exception e) {
            logger.error("Failed to Update Customer");
            e.printStackTrace();
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
                    logger.info("Customer found");
                } else {
                    writer.write("{\"error\": \"Customer not found\"}");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    logger.error("Customer not found");
                }
            } else {
                List<CustomerDTO> customers = customerBO.getAllCustomers();
                List<String> appointmentIds = customerBO.getAppointmentIds();
                Map<String, Object> result = new HashMap<>();
                result.put("customers", customers);
                result.put("appointments", appointmentIds);
                writer.write(jsonb.toJson(result));
                resp.setStatus(HttpServletResponse.SC_OK);
                logger.info("All Customers found");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Get Customers");
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
                logger.error("Customer ID is missing");
                return;
            }

            String [] split = pathInfo.split("/");
            if (split.length != 2) {
                writer.write("Invalid Customer ID");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Invalid Customer ID");
                return;
            }

            String customerId = split[1];

            if (customerBO.deleteCustomer(customerId)) {
                writer.write("Customer Delete successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info("Customer Deleted Successfully");
            } else {
                writer.write("Customer Delete failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Failed to Delete Customer");
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Delete Customer");
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        logger.info("Customer Controller Destroyed");
        super.destroy();
    }
}
