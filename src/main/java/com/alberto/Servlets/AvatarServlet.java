package com.alberto.Servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alberto.DB.DAOs.UsuariosDAO;
import com.alberto.Model.Usuario;


@WebServlet("/avatar")
public class AvatarServlet extends HttpServlet {

    private static final String RUTA_UPLOAD = System.getProperty("user.home") + File.separator + "app_uploads";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if(idParam == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UsuariosDAO dao = new UsuariosDAO();
        Usuario img = dao.obtenerPorId(Integer.parseInt(idParam));

        if(img == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        File archivo = new File(RUTA_UPLOAD, img.getAvatar());
        if(!archivo.exists()){
            try (java.io.InputStream is = getServletContext().getResourceAsStream("/Imagenes/default.png")) {
                if (is != null) {
                    resp.setContentType("image/png");
                    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                    resp.setHeader("Pragma", "no-cache");
                    resp.setDateHeader("Expires", 0);
                    try (OutputStream out = resp.getOutputStream()) {
                        byte[] buffer = new byte[8192];
                        int bytes;
                        while((bytes = is.read(buffer)) != -1){
                            out.write(buffer, 0, bytes);
                        }
                    }
                    return;
                }
            }
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mime = getServletContext().getMimeType(archivo.getName());
        if(mime == null) mime = "application/octet-stream";

        resp.setContentType(mime);
        resp.setContentLengthLong(archivo.length());

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache"); 
        resp.setDateHeader("Expires", 0);
        try(FileInputStream in = new FileInputStream(archivo);
            OutputStream out = resp.getOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytes;
            while((bytes = in.read(buffer)) != -1){
                out.write(buffer, 0, bytes);
            }
        }
    }
}
