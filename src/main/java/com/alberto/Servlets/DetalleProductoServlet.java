package com.alberto.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alberto.DB.Factoria.FactoryDAOS;
import com.alberto.DB.DAOs.ProductosDAO;
import com.alberto.Model.Producto;
import com.alberto.DTO.ProductoDTO;

/**
 * Servlet encargado de mostrar los detalles completos de un producto.
 * Recupera el producto mediante su ID desde la base de datos y lo envía a la vista.
 */
@WebServlet("/detalle")
public class DetalleProductoServlet extends HttpServlet {

    /**
     * Procesa la solicitud GET para ver un producto.
     *
     * @param req  La petición HTTP que contiene el parámetro 'id' del producto.
     * @param resp La respuesta HTTP que carga el JSP del detalle o un error 404/400.
     * @throws ServletException En caso de un problema interno de Servlet.
     * @throws IOException      En caso de problemas de I/O en despacho.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            ProductosDAO dao = FactoryDAOS.crearProductosDAO();
            Producto p = dao.obtenerPorId(id);
            
            if (p != null) {
                ProductoDTO productoDTO = new ProductoDTO(p);
                req.setAttribute("producto", productoDTO);
                req.getRequestDispatcher("/detalle.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de producto inválido");
        }
    }
}
