package com.alberto.Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alberto.DB.DAOs.PedidoDAO;
import com.alberto.DTO.ItemCarritoDTO;
import com.alberto.DB.Factoria.FactoryDAOS;

/**
 * Servlet encargado de obtener y mostrar los detalles de un pedido específico.
 * Extrae las líneas de un pedido dado su ID valiéndose de {@link PedidoDAO}.
 */
@WebServlet("/detalle-pedido")
public class DetallePedidoServlet extends HttpServlet {

    private PedidoDAO pedidoDAO;

    /**
     * Inicializa el Servlet configurando el DAO necesario.
     *
     * @throws ServletException Si hay un error al inicializar el DAO o el Servlet.
     */
    @Override
    public void init() throws ServletException {
        this.pedidoDAO = FactoryDAOS.crearPedidoDAO();
    }

    /**
     * Procesa la solicitud GET para ver los ítems de un pedido que pertenece al usuario en sesión.
     *
     * @param req  La petición HTTP que incluye el parámetro 'id' del pedido.
     * @param resp La respuesta HTTP que carga el JSP del detalle o renderiza un mensaje de error.
     * @throws ServletException En caso de problema en el despacho de vistas.
     * @throws IOException      En caso de errores de escritura al ServletResponse.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("<p>Error: No autorizado.</p>");
            return;
        }

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("<p>Error: ID de pedido no proporcionado.</p>");
            return;
        }

        try {
            int idPedido = Integer.parseInt(idParam);
            List<ItemCarritoDTO> lineas = pedidoDAO.obtenerLineasPedido(idPedido);
            
            req.setAttribute("lineasPedido", lineas);
            req.getRequestDispatcher("detalle_pedido.jsp").forward(req, resp);
            
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("<p>Error: ID de pedido inválido.</p>");
        }
    }
}
