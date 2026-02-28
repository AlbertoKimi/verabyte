package com.alberto.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alberto.Service.AuthService;
import com.alberto.DTO.UserSessionDTO;
import com.alberto.DB.DAOs.CarritoDAO;
import com.alberto.DTO.CarritoDTO;
import com.alberto.DTO.ItemCarritoDTO;
import com.alberto.Utils.CookieUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() throws ServletException {
        this.authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "Email y contraseña son obligatorios");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        UserSessionDTO userSession = authService.login(email, password);

        if (userSession != null) {
            HttpSession session = req.getSession();
            session.setAttribute("usuario", userSession);

            CarritoDTO carritoSesion = (CarritoDTO) session.getAttribute("carrito");
            CarritoDAO carritoDAO = new CarritoDAO();
            CarritoDTO carritoDB = carritoDAO.cargarCarrito(userSession.getUserId().intValue());

            if (carritoDB == null) {
                carritoDB = new CarritoDTO();
            }

            if (carritoSesion != null && !carritoSesion.getItems().isEmpty()) {
                for (ItemCarritoDTO item : carritoSesion.getItems()) {
                    carritoDB.addItem(item);
                }
            }

            carritoDAO.guardarCarrito(userSession.getUserId().intValue(), carritoDB);
            session.setAttribute("carrito", carritoDB);

            Map<Integer, Integer> mapaCarrito = new HashMap<>();
            for (ItemCarritoDTO i : carritoDB.getItems()) {
                mapaCarrito.put(i.getIdProducto(), i.getCantidad());
            }
            
            String cookieValue = CookieUtils.encodeMapForCookie(mapaCarrito);
            Cookie cookie = new Cookie("carrito", cookieValue);
            cookie.setMaxAge(60 * 60 * 24 * 2);
            cookie.setPath("/");
            resp.addCookie(cookie);

             String redirectUrl = (String) session.getAttribute("redirectUrl");
            if (redirectUrl != null) {
                session.removeAttribute("redirectUrl");
                resp.sendRedirect(redirectUrl);
            } else {
                resp.sendRedirect(req.getContextPath() + "/index");
            }
        } else {
            req.setAttribute("error", "Credenciales incorrectas");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
