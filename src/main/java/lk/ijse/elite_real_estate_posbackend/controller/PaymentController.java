package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.BOFactory;
import lk.ijse.elite_real_estate_posbackend.bo.custom.PaymentBO;
import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;
import lk.ijse.elite_real_estate_posbackend.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/payment/*", loadOnStartup = 1)
public class PaymentController extends HttpServlet {
    private final PaymentBO paymentBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);
    static Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Invalid Content Type");
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            PaymentDTO payment = jsonb.fromJson(req.getReader(), PaymentDTO.class);

            writer.write(paymentBO.savePayment(payment));
            updatePropertyStatus(payment.getProId());
            updateAppointmentStatus(payment.getCusId());
            resp.setStatus(HttpServletResponse.SC_CREATED);
            logger.info("Payment Added Successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Add Payment");
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();

            List<PropertyDTO> properties = paymentBO.getAllProperties();
            List<CustomerDTO> customers = paymentBO.getAllCustomers();
            List<String> generatedId = Collections.singletonList(paymentBO.generatePaymentID());

            System.out.println("Properties: " + properties);
            System.out.println("Customers: " + customers);
            System.out.println("Generated ID: " + generatedId);

            Map<String, Object> result = new HashMap<>();
            result.put("properties", properties);
            result.put("customers", customers);
            result.put("paymentId", generatedId);

            writer.write(jsonb.toJson(result));
            resp.setStatus(HttpServletResponse.SC_OK);
            logger.info("All Properties and Customers Retrieved Successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Retrieve Properties and Customers");
            e.printStackTrace();
        }
    }

    private void updatePropertyStatus(String proId) {
        boolean flag = paymentBO.updatePropertyStatus(proId);
        if (flag) {
            logger.info("Property Status Updated Successfully");
        } else {
            logger.error("Failed to Update Property Status");
        }
    }

    private void updateAppointmentStatus(String cusId) {
        boolean flag = paymentBO.updateAppointmentStatus(cusId);
        if (flag) {
            logger.info("Appointment Status Updated Successfully");
        } else {
            logger.error("Failed to Update Appointment Status");
        }
    }

    @Override
    public void destroy() {
        logger.info("Payment Controller Destroyed");
        super.destroy();
    }
}
