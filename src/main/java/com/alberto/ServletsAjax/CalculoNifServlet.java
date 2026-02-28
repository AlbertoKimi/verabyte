package com.alberto.ServletsAjax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/CalculoNifServlet")
public class CalculoNifServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dniNumero = request.getParameter("dni");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, String> jsonResponse = new HashMap<>();

        if (dniNumero != null && dniNumero.matches("\\d{7,8}")) {
            try {
                int dni = Integer.parseInt(dniNumero);
                String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
                char letra = letras.charAt(dni % 23);
                
                jsonResponse.put("letra", String.valueOf(letra));
                jsonResponse.put("nifCompleto", dniNumero + letra);
                jsonResponse.put("status", "ok");
            } catch (NumberFormatException e) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Formato inválido");
            }
        } else {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Número inválido");
        }

        String json = new Gson().toJson(jsonResponse);
        out.print(json);
        out.flush();
    }
}
