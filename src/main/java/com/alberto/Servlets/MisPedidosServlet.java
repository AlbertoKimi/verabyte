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
import com.alberto.DTO.PedidoDTO;
import com.alberto.DTO.UserSessionDTO;

import com.alberto.DB.Factoria.FactoryDAOS;

@WebServlet(name = "MisPedidosServlet", urlPatterns = {"/mis-pedidos"})
public class MisPedidosServlet extends HttpServlet {

    private PedidoDAO pedidoDAO;

    @Override
    public void init() throws ServletException {
        this.pedidoDAO = FactoryDAOS.crearPedidoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        UserSessionDTO usuario = (UserSessionDTO) session.getAttribute("usuario");
        
        List<PedidoDTO> pedidos = pedidoDAO.listarPorUsuario(usuario.getUserId().intValue());
        req.setAttribute("misPedidos", pedidos);
        
        req.getRequestDispatcher("pedidos.jsp").forward(req, resp);
    }
}
