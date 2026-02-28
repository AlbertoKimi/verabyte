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

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

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
