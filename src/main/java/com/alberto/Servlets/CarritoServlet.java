package com.alberto.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alberto.DTO.CarritoDTO;
import com.alberto.DTO.ItemCarritoDTO;
import com.alberto.Utils.CookieUtils;
import com.alberto.DB.DAOs.CarritoDAO;
import com.alberto.DTO.UserSessionDTO;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;

@WebServlet("/carrito")
public class CarritoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int idProducto = Integer.parseInt(req.getParameter("idProducto"));
            String nombre = req.getParameter("nombre");
            double precio = Double.parseDouble(req.getParameter("precio"));
            String imagen = req.getParameter("imagen");

            HttpSession session = req.getSession();
            
            CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new CarritoDTO();
            }

            ItemCarritoDTO item = new ItemCarritoDTO(idProducto, nombre, precio, 1, imagen);
            carrito.addItem(item);

            session.setAttribute("carrito", carrito);

            UserSessionDTO user = (UserSessionDTO) session.getAttribute("usuario");
            if (user != null) {
                CarritoDAO carritoDAO = new CarritoDAO();
                carritoDAO.guardarCarrito(user.getUserId().intValue(), carrito);
            }

            Map<Integer, Integer> mapaCarrito = new HashMap<>();
            for (ItemCarritoDTO i : carrito.getItems()) {
                mapaCarrito.put(i.getIdProducto(), i.getCantidad());
            }
            
            String cookieValue = CookieUtils.encodeMapForCookie(mapaCarrito);
            Cookie cookie = new Cookie("carrito", cookieValue);
            cookie.setMaxAge(60 * 60 * 24 * 2); 
            cookie.setPath("/"); 
            resp.addCookie(cookie);
            
            session.setAttribute("message", "¡Producto añadido al carrito correctamente!");

        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("message", "Error al añadir el producto al carrito.");
        }
        
        resp.sendRedirect("lista");
    }
}
