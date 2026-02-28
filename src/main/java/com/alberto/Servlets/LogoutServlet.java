package com.alberto.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alberto.Utils.CookieUtils;
import com.alberto.DB.DAOs.CarritoDAO;
import com.alberto.DTO.CarritoDTO;
import com.alberto.DTO.UserSessionDTO;

/**
 * Servlet que gestiona el cierre de sesión de un usuario.
 * Guarda el estado actual del carrito en base de datos, invalida la sesión
 * HTTP y elimina la cookie temporal del navegador.
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    /**
     * Procesa la solicitud GET para cerrar sesión.
     *
     * @param req  La petición HTTP de la cual se va a invalidar la sesión.
     * @param resp La respuesta HTTP que redirigirá al inicio tras terminar el proceso.
     * @throws ServletException Errores generados dentro del Servlet.
     * @throws IOException      Errores provenientes del manejo de red o redirección.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            UserSessionDTO usuario = (UserSessionDTO) session.getAttribute("usuario");
            CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");
            
            if (usuario != null) {
                CarritoDAO carritoDAO = new CarritoDAO();
                try {
                    if (carrito != null) {
                        carritoDAO.guardarCarrito(usuario.getUserId().intValue(), carrito);
                    } else {
                        carritoDAO.guardarCarrito(usuario.getUserId().intValue(), new CarritoDTO());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            session.invalidate();
        }

        CookieUtils.deleteCookie(req, resp, "carrito");

        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
