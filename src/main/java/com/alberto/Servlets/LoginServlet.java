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

/**
 * Servlet que gestiona el inicio de sesión de los usuarios.
 * Valida credenciales, inicializa la sesión HTTP, carga el carrito guardado
 * previamente en base de datos y genera las cookies asociadas al navegador.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private AuthService authService;

    /**
     * Inicializa el Servlet preparando el servicio de autenticación {@link AuthService}.
     *
     * @throws ServletException Si falla la inicialización.
     */
    @Override
    public void init() throws ServletException {
        this.authService = new AuthService();
    }

    /**
     * Procesa la solicitud GET para acceder a la página de login.
     * Si el usuario ya tiene sesión, lo redirige al inicio.
     *
     * @param req  La petición HTTP.
     * @param resp La respuesta HTTP que despacha al formulario de login.
     * @throws ServletException En caso de problema en el despacho de vistas.
     * @throws IOException      En error de red o I/O.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    /**
     * Procesa la solicitud POST enviada desde el formulario de login.
     * Verifica email y contraseña. Si son correctos, carga el carrito de la base
     * de datos, une o sobreescribe con el de la sesión actual y redirige a la
     * página correspondiente. En caso incorrecto, devuelve un error al JSP.
     *
     * @param req  Petición HTTP con los parámetros 'email' y 'password'.
     * @param resp Respuesta HTTP.
     * @throws ServletException En caso de error de servlet.
     * @throws IOException      En caso de error al redirigir o despachar.
     */
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
