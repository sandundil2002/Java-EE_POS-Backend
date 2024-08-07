package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.PaymentBOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.CustomerDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;
import lk.ijse.elite_real_estate_posbackend.dto.PropertyDTO;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/payment/*", loadOnStartup = 1)
public class PaymentController extends HttpServlet {
    private final PaymentBOIMPL paymentBOIMPL = new PaymentBOIMPL();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            PaymentDTO payment = jsonb.fromJson(req.getReader(), PaymentDTO.class);

            writer.write(paymentBOIMPL.savePayment(payment));
            updatePropertyStatus(payment.getProId());
            updateAppointmentStatus(payment.getCusId());
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();

            List<PropertyDTO> properties = paymentBOIMPL.getAllProperties();
            List<CustomerDTO> customers = paymentBOIMPL.getAllCustomers();
            List<String> generatedId = Collections.singletonList(paymentBOIMPL.generatePaymentID());

            System.out.println("Properties: " + properties);
            System.out.println("Customers: " + customers);
            System.out.println("Generated ID: " + generatedId);

            Map<String, Object> result = new HashMap<>();
            result.put("properties", properties);
            result.put("customers", customers);
            result.put("paymentId", generatedId);

            writer.write(jsonb.toJson(result));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    private void updatePropertyStatus(String proId) {
        paymentBOIMPL.updatePropertyStatus(proId);
    }

    private void updateAppointmentStatus(String cusId) {
         paymentBOIMPL.updateAppointmentStatus(cusId);
    }

}
