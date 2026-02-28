package com.alberto.Filters;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alberto.DB.DAOs.ProductosDAO;
import com.alberto.DTO.CarritoDTO;
import com.alberto.DTO.ItemCarritoDTO;
import com.alberto.Model.Producto;
import com.alberto.Utils.CookieUtils;

import com.alberto.DB.Factoria.FactoryDAOS;

@WebFilter("/*")
public class CarritoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        if (session.getAttribute("carrito") == null) {
            Cookie cookieCarrito = CookieUtils.getCookie(httpRequest, "carrito");

            if (cookieCarrito != null && cookieCarrito.getValue() != null && !cookieCarrito.getValue().isEmpty()) {
                ProductosDAO productosDAO = FactoryDAOS.crearProductosDAO();
                List<Producto> todosLosProductos = productosDAO.listarTodos();

                Map<Producto, Integer> itemsEnCookie = CookieUtils.decodeCookieIntValue(cookieCarrito, todosLosProductos);

                if (!itemsEnCookie.isEmpty()) {
                    CarritoDTO carrito = new CarritoDTO();
                    
                    for (Map.Entry<Producto, Integer> entry : itemsEnCookie.entrySet()) {
                        Producto p = entry.getKey();
                        int cantidad = entry.getValue();
                        
                        ItemCarritoDTO item = new ItemCarritoDTO(
                            p.getIdProducto(),
                            p.getNombre(),
                            p.getPrecio(),
                            cantidad,
                            p.getImagen()
                        );
                        carrito.addItem(item);
                    }
                    
                    session.setAttribute("carrito", carrito);
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
