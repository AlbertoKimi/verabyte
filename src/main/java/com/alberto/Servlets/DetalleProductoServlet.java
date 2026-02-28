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

@WebServlet("/detalle")
public class DetalleProductoServlet extends HttpServlet {

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
