package com.alberto.ServletsAjax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alberto.DB.DAOs.UsuariosDAO;
import com.google.gson.JsonObject;

/**
 * Servlet AJAX utilizado para verificar dinámicamente si un correo electrónico
 * ya se encuentra registrado en la base de datos (durante el registro o edición).
 */
@WebServlet("/CheckEmailServlet")
public class CheckEmailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Procesa la petición GET de AJAX. 
     * Consulta con {@link UsuariosDAO} si el mail existe y responde un JSON con el resultado (Booleano).
     *
     * @param request  La petición HTTP con parámetro 'email'.
     * @param response Respuesta HTTP con contenido application/json y el resultado 'exists'.
     * @throws ServletException Excepción estándar de Servlet.
     * @throws IOException      Excepción de manejo de salida de datos en red.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        if (email != null && !email.trim().isEmpty()) {
            UsuariosDAO dao = new UsuariosDAO();
            boolean exists = dao.existeEmail(email);
            jsonResponse.addProperty("exists", exists);
        } else {
            jsonResponse.addProperty("error", "Falta poner el mail");
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}
