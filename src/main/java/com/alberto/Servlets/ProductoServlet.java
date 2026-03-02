package com.alberto.Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alberto.DB.DAOs.ProductosDAO;
import com.alberto.DB.DAOs.CategoriasDAO;
import com.alberto.DTO.ProductoDTO;
import com.alberto.Model.Producto;
import java.util.ArrayList;

import com.alberto.DB.Factoria.FactoryDAOS;

/**
 * Servlet principal que maneja la vista del catálogo de productos.
 * Recibe y aplica filtros (categoría, marca, precio, nombre) al listado general.
 */
@WebServlet(urlPatterns = {"", "/lista", "/index", "/home"})
public class ProductoServlet extends HttpServlet{
    /**
     * Procesa la solicitud GET para listar los productos en la página principal,
     * aplicando los parámetros de filtrado si están presentes.
     *
     * @param req  La petición HTTP. Puede contener parámetros de filtros.
     * @param resp La respuesta HTTP que carga el JSP del catálogo.
     * @throws ServletException En caso de problema al mostrar la vista.
     * @throws IOException      En caso de problema de I/O.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String categoria = req.getParameter("categoria");
        String marca = req.getParameter("marca");
        String precioMinStr = req.getParameter("precioMin");
        String precioMaxStr = req.getParameter("precioMax");
        String nombre = req.getParameter("nombre");

        Double precioMin = null;
        Double precioMax = null;

        if (precioMinStr != null && !precioMinStr.isEmpty()) {
            try {
                precioMin = Double.parseDouble(precioMinStr);
                if (precioMin < 0) {
                    precioMin = 0.0;
                    precioMinStr = "0";
                }
            } catch (NumberFormatException e) {

            }
        }
        
        if (precioMaxStr != null && !precioMaxStr.isEmpty()) {
            try {
                precioMax = Double.parseDouble(precioMaxStr);
                if (precioMax < 0) {
                    precioMax = 0.0;
                    precioMaxStr = "0";
                }
            } catch (NumberFormatException e) {

            }
        }

        ProductosDAO productosDAO = FactoryDAOS.crearProductosDAO();
        List<Producto> productos = productosDAO.listarTodos();

        productos = productosDAO.filtrarPorCategoria(productos, categoria);
        productos = productosDAO.filtrarPorMarca(productos, marca);
        productos = productosDAO.filtrarPorPrecio(productos, precioMin, precioMax);
        productos = productosDAO.filtrarPorNombre(productos, nombre);

        List<ProductoDTO> productosDTO = new ArrayList<>();
        if (productos != null) {
            for (Producto p : productos) {
                productosDTO.add(new ProductoDTO(p));
            }
        }

        CategoriasDAO categoriasDAO = FactoryDAOS.crearCategoriasDAO();
        req.setAttribute("categorias", categoriasDAO.listarTodas());
        req.setAttribute("marcas", productosDAO.obtenerMarcas());

        req.setAttribute("paramCategoria", categoria);
        req.setAttribute("paramMarca", marca);
        req.setAttribute("paramPrecioMin", precioMinStr);
        req.setAttribute("paramPrecioMax", precioMaxStr);
        req.setAttribute("paramNombre", nombre);

        req.setAttribute("productos", productosDTO);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
