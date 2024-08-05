package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.AppointmentBOIMPL;
import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/appointment", loadOnStartup = 1)
public class AppointmentController extends HttpServlet {
    private final AppointmentBOIMPL appointmentBOImpl = new AppointmentBOIMPL();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            AppointmentDTO appointment = jsonb.fromJson(req.getReader(), AppointmentDTO.class);            writer.write(appointmentBOImpl.saveAppointment(appointment));
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
            System.out.println(appointment.toString());

            if(appointmentBOImpl.updateAppointment(appointmentId,appointment)){
                write.write("Appointment update successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                write.write("Appointment update failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String appointmentId = req.getParameter("appId");

        if (appointmentId != null) {
            try (var writer = resp.getWriter()) {
                var appointment = appointmentBOImpl.searchAppointment(appointmentId);
                if (appointment != null) {
                    Jsonb jsonb = JsonbBuilder.create();
                    writer.write(jsonb.toJson(appointment));
                } else {
                    writer.write("Appointment not found");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try (var writer = resp.getWriter()) {
                List<AppointmentDTO> appointments = appointmentBOImpl.getAllAppointments();
                Jsonb jsonb = JsonbBuilder.create();
                writer.write(jsonb.toJson(appointments));
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            var appointmentId = req.getParameter("appId");

            if(appointmentBOImpl.deleteAppointment(appointmentId)){
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

    @Override
    public void destroy() {
        super.destroy();
    }
}
