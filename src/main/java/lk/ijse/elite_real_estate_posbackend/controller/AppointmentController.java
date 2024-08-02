package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.AppointmentBOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/appointment", loadOnStartup = 1)
public class AppointmentController extends HttpServlet {
    private final AppointmentBOIMPL appointmentBOImpl = new AppointmentBOIMPL();
    Connection connection;

    @Override
    public void init() {
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/elite_real_estate_pos");
            this.connection = pool.getConnection();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            AppointmentDTO appointment = jsonb.fromJson(req.getReader(), AppointmentDTO.class);

            writer.write(appointmentBOImpl.saveAppointment(appointment,connection));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp){
        try (var write = resp.getWriter()) {
            var appointmentId = req.getParameter("appId");
            Jsonb jsonb = JsonbBuilder.create();
            AppointmentDTO appointment = jsonb.fromJson(req.getReader(), AppointmentDTO.class);

            if(appointmentBOImpl.updateAppointment(appointmentId,appointment,connection)){
                write.write("Appointment update successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                write.write("Appointment update failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();

            var appointmentId = req.getParameter("appId");
            resp.setContentType("application/json");
            jsonb.toJson(appointmentBOImpl.searchAppointment(appointmentId,connection),writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            var appointmentId = req.getParameter("appId");

            if(appointmentBOImpl.deleteAppointment(appointmentId,connection)){
                writer.write("Appointment Delete successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Delete failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
