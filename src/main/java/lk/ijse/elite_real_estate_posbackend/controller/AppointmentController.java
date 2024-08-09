package lk.ijse.elite_real_estate_posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.elite_real_estate_posbackend.bo.custom.AppointmentBO;
import lk.ijse.elite_real_estate_posbackend.bo.BOFactory;
import lk.ijse.elite_real_estate_posbackend.dto.AppointmentDTO;
import lk.ijse.elite_real_estate_posbackend.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/appointment/*", loadOnStartup = 1)
public class AppointmentController extends HttpServlet {
    private final AppointmentBO appointmentBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.APPOINTMENT);
    static Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Invalid Content Type");
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            AppointmentDTO appointment = jsonb.fromJson(req.getReader(), AppointmentDTO.class);
            writer.write(appointmentBO.saveAppointment(appointment));
            resp.setStatus(HttpServletResponse.SC_CREATED);
            logger.info("Appointment Added Successfully");
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Add Appointment");
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

            if (appointmentBO.updateAppointment(appointmentId, appointment)) {
                writer.write("Appointment update successful");
                resp.setStatus(HttpServletResponse.SC_OK);
                logger.info("Appointment Updated Successfully");
            } else {
                writer.write("Appointment update failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Failed to Update Appointment");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Update Appointment");
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String appointmentId = req.getParameter("appId");

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();

            if (appointmentId != null) {
                var appointment = appointmentBO.searchAppointment(appointmentId);
                if (appointment != null) {
                    writer.write(jsonb.toJson(appointment));
                    logger.info("Appointment found");
                } else {
                    writer.write("Appointment not found");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    logger.error("Appointment not found");
                }
            } else {
                List<AppointmentDTO> appointments = appointmentBO.getAllAppointments();
                List<String> adminIds = appointmentBO.getAdminIds();
                Map<String, Object> result = new HashMap<>();
                result.put("appointments", appointments);
                result.put("adminIds", adminIds);
                writer.write(jsonb.toJson(result));
                resp.setStatus(HttpServletResponse.SC_OK);
                logger.info("All Appointments found");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to find Appointment");
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Appointment ID is missing");
                logger.error("Appointment ID is missing");
                return;
            }

            String[] splits = pathInfo.split("/");
            if (splits.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid appointment ID");
                logger.error("Invalid appointment ID");
                return;
            }

            String appointmentId = splits[1];

            if (appointmentBO.deleteAppointment(appointmentId)) {
                writer.write("Appointment delete successful");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info("Appointment Deleted Successfully");
            } else {
                writer.write("Delete failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Failed to Delete Appointment");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Failed to Delete Appointment");
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        logger.info("Appointment Controller Destroyed");
        super.destroy();
    }
}
