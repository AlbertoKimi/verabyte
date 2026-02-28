package com.alberto.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alberto.DB.DAOs.CarritoDAO;
import com.alberto.DTO.CarritoDTO;
import com.alberto.DTO.UserSessionDTO;
import com.alberto.Utils.CookieUtils;

import com.alberto.DB.Factoria.FactoryDAOS;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserSessionDTO usuario = (UserSessionDTO) session.getAttribute("usuario");

        if (usuario == null) {
            session.setAttribute("redirectUrl", req.getContextPath() + "/carrito.jsp");
            resp.sendRedirect("login.jsp");
            return;
        }

        CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");
        if (carrito == null || carrito.getItems().isEmpty()) {
            session.setAttribute("message", "El carrito está vacío.");
            resp.sendRedirect("carrito.jsp");
            return;
        }

        CarritoDAO carritoDAO = FactoryDAOS.crearCarritoDAO();
        boolean exito = carritoDAO.finalizarCompra(usuario.getUserId().intValue(), carrito);

        if (exito) {
            session.removeAttribute("carrito");
            
            CookieUtils.deleteCookie(req, resp, "carrito");

            session.setAttribute("message", "¡Compra finalizada con éxito! Gracias por tu pedido.");
            resp.sendRedirect("lista"); 
        } else {
            session.setAttribute("message", "Hubo un error al procesar tu pedido. Inténtalo de nuevo.");
            resp.sendRedirect("carrito.jsp");
        }
    }
}
