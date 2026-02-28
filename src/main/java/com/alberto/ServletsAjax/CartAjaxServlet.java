package com.alberto.ServletsAjax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alberto.DTO.CarritoDTO;
import com.alberto.DTO.ItemCarritoDTO;
import com.alberto.Utils.CookieUtils;
import com.alberto.DTO.UserSessionDTO;
import com.alberto.DB.DAOs.CarritoDAO;

/**
 * Servlet AJAX para el manejo dinámico del carrito (aumentar, disminuir,
 * eliminar items o vaciar por completo). Retorna respuestas en formato JSON.
 */
@WebServlet("/ajax/cart")
public class CartAjaxServlet extends HttpServlet {

    /**
     * Recibe peticiones POST asíncronas para modificar el estado del carrito.
     * Lee la acción deseada ('aumentar', 'disminuir', 'eliminar', 'vaciar')
     * y el ID del producto, ajustando la sesión, cookies y BD según sea necesario.
     *
     * @param req  Petición HTTP con los parámetros 'action' y 'idProducto'.
     * @param resp Respuesta HTTP que enviará el estado actualizado en formato JSON.
     * @throws ServletException Excepción general del Servlet.
     * @throws IOException      Excepción general de I/O.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String action = req.getParameter("action");
        String idStr = req.getParameter("idProducto");

        if (action == null) {
            out.print("{\"status\":\"error\", \"message\":\"Acción no especificada\"}");
            return;
        }

        HttpSession session = req.getSession();
        CarritoDTO carrito = (CarritoDTO) session.getAttribute("carrito");

        if (carrito == null) {
            out.print("{\"status\":\"error\", \"message\":\"Carrito no encontrado\"}");
            return;
        }
        
        if ("vaciar".equals(action)) {
            carrito.getItems().clear();
            
            Cookie cookie = new Cookie("carrito", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            resp.addCookie(cookie);
            
            session.setAttribute("carrito", carrito);

            UserSessionDTO user = (UserSessionDTO) session.getAttribute("usuario");
            if (user != null) {
                CarritoDAO carritoDAO = new CarritoDAO();
                carritoDAO.guardarCarrito(user.getUserId().intValue(), carrito);
            }

            out.print("{\"status\":\"success\", \"total\":0.0}");
            return;
        }

        if (idStr == null) {
            out.print("{\"status\":\"error\", \"message\":\"Parámetros inválidos\"}");
            return;
        }

        int idProducto = Integer.parseInt(idStr);

        boolean itemModified = false;
        ItemCarritoDTO modifiedItem = null;
        if ("aumentar".equals(action)) {
            for (ItemCarritoDTO item : carrito.getItems()) {
                if (item.getIdProducto() == idProducto) {
                    item.setCantidad(item.getCantidad() + 1);
                    itemModified = true;
                    modifiedItem = item;
                    break;
                }
            }
        } else if ("disminuir".equals(action)) {
            for (int i = 0; i < carrito.getItems().size(); i++) {
                ItemCarritoDTO item = carrito.getItems().get(i);
                if (item.getIdProducto() == idProducto) {
                    if (item.getCantidad() > 1) {
                        item.setCantidad(item.getCantidad() - 1);
                    }
                    itemModified = true;
                    modifiedItem = item;
                    break;
                }
            }
        } else if ("eliminar".equals(action)) {
            for (int i = 0; i < carrito.getItems().size(); i++) {
                ItemCarritoDTO item = carrito.getItems().get(i);
                if (item.getIdProducto() == idProducto) {
                    carrito.getItems().remove(i);
                    itemModified = true;
                    break;
                }
            }
        }

        if (itemModified) {

            Map<Integer, Integer> mapaCarrito = new HashMap<>();
            for (ItemCarritoDTO i : carrito.getItems()) {
                mapaCarrito.put(i.getIdProducto(), i.getCantidad());
            }
            String cookieValue = CookieUtils.encodeMapForCookie(mapaCarrito);
            Cookie cookie = new Cookie("carrito", cookieValue);
            cookie.setMaxAge(60 * 60 * 24 * 2); // 2 días
            cookie.setPath("/");
            resp.addCookie(cookie);
            
            session.setAttribute("carrito", carrito);

            UserSessionDTO user = (UserSessionDTO) session.getAttribute("usuario");
            if (user != null) {
                CarritoDAO carritoDAO = new CarritoDAO();
                carritoDAO.guardarCarrito(user.getUserId().intValue(), carrito);
            }
            
            double itemSubtotal = (modifiedItem != null) ? (modifiedItem.getPrecio() * modifiedItem.getCantidad()) : 0.0;
            int itemCantidad = (modifiedItem != null) ? modifiedItem.getCantidad() : 0;
            
            out.print("{\"status\":\"success\", \"total\":" + carrito.getTotal() + ", \"itemSubtotal\":" + itemSubtotal + ", \"itemCantidad\":" + itemCantidad + "}");
        } else {
             out.print("{\"status\":\"error\", \"message\":\"Producto no encontrado en carrito\"}");
        }
    }
}
