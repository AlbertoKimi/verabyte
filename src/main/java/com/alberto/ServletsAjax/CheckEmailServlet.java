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

@WebServlet("/CheckEmailServlet")
public class CheckEmailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
