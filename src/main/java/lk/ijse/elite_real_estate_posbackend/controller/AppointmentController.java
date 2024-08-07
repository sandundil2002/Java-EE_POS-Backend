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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/appointment/*", loadOnStartup = 1)
public class AppointmentController extends HttpServlet {
    private final AppointmentBOIMPL appointmentBOImpl = new AppointmentBOIMPL();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            AppointmentDTO appointment = jsonb.fromJson(req.getReader(), AppointmentDTO.class);
            writer.write(appointmentBOImpl.saveAppointment(appointment));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                writer.write("Appointment ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String appointmentId = pathInfo.substring(1);
            if (appointmentId.isEmpty()) {
                writer.write("Appointment ID is missing");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Jsonb jsonb = JsonbBuilder.create();
            AppointmentDTO appointment = jsonb.fromJson(req.getReader(), AppointmentDTO.class);

            if (appointmentBOImpl.updateAppointment(appointmentId, appointment)) {
                writer.write("Appointment update successful");
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                writer.write("Appointment update failed");
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

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();

            if (appointmentId != null) {
                var appointment = appointmentBOImpl.searchAppointment(appointmentId);
                if (appointment != null) {
                    writer.write(jsonb.toJson(appointment));
                } else {
                    writer.write("Appointment not found");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                List<AppointmentDTO> appointments = appointmentBOImpl.getAllAppointments();
                List<String> adminIds = appointmentBOImpl.getAdminIds();
                Map<String, Object> result = new HashMap<>();
                result.put("appointments", appointments);
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
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Appointment ID is missing");
                return;
            }

            String[] splits = pathInfo.split("/");
            if (splits.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid appointment ID");
                return;
            }

            String appointmentId = splits[1];

            if (appointmentBOImpl.deleteAppointment(appointmentId)) {
                writer.write("Appointment delete successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                writer.write("Delete failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
