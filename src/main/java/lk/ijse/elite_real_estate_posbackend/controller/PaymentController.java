package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.PaymentBOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.PaymentDTO;

@WebServlet(urlPatterns = "/payment", loadOnStartup = 1)
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
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
