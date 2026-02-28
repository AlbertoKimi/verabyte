package com.alberto.Servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.alberto.DTO.UserSessionDTO;
import com.alberto.DB.DAOs.UsuariosDAO;
import com.alberto.Model.Usuario;
import com.alberto.Utils.Validaciones;
import com.alberto.Security.PasswordHasherArgon2;

@WebServlet("/UpdateUserServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 5, 
        maxFileSize = 1024 * 1024 * 20,      
        maxRequestSize = 1024 * 1024 * 30   
)
public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String RUTA_UPLOAD = System.getProperty("user.home") + File.separator + "app_uploads";

    private static final Set<String> TIPOS_PERMITIDOS = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("usuario");

        if (userSession == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UsuariosDAO usuariosDAO = new UsuariosDAO();
        Usuario usuarioActual = usuariosDAO.obtenerPorId(userSession.getUserId().intValue());

        if (usuarioActual == null) {
            session.invalidate();
            response.sendRedirect("login.jsp");
            return;
        }

        if (session.getAttribute("errores") == null) {
            Map<String, String> formulario = new HashMap<>();
            formulario.put("email", usuarioActual.getEmail() != null ? usuarioActual.getEmail() : "");
            formulario.put("nombre", usuarioActual.getNombre() != null ? usuarioActual.getNombre() : "");
            formulario.put("apellidos", usuarioActual.getApellidos() != null ? usuarioActual.getApellidos() : "");
            formulario.put("nif", usuarioActual.getNif() != null ? usuarioActual.getNif() : "");
            formulario.put("telefono", usuarioActual.getTelefono() != null ? usuarioActual.getTelefono() : "");
            formulario.put("direccion", usuarioActual.getDireccion() != null ? usuarioActual.getDireccion() : "");
            formulario.put("cp", usuarioActual.getCodigoPostal() != null ? usuarioActual.getCodigoPostal() : "");
            formulario.put("localidad", usuarioActual.getLocalidad() != null ? usuarioActual.getLocalidad() : "");
            formulario.put("provincia", usuarioActual.getProvincia() != null ? usuarioActual.getProvincia() : "");

            session.setAttribute("formulario", formulario);
        }

        request.setAttribute("isEdit", true);
        request.getRequestDispatcher("registro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("usuario");

        if (userSession == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UsuariosDAO usuariosDAO = new UsuariosDAO();
        Usuario usuarioActual = usuariosDAO.obtenerPorId(userSession.getUserId().intValue());

        if (usuarioActual == null) {
            session.invalidate();
            response.sendRedirect("login.jsp");
            return;
        }

        session.removeAttribute("errores");
        session.removeAttribute("formulario");

        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String cp = request.getParameter("cp");
        String localidad = request.getParameter("localidad");
        String provincia = request.getParameter("provincia");
        
        String currentPassword = request.getParameter("current_password");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        
        Map<String, String> formulario = new HashMap<>();
        formulario.put("email", usuarioActual.getEmail());
        formulario.put("nif", usuarioActual.getNif());
        formulario.put("nombre", nombre);
        formulario.put("apellidos", apellidos);
        formulario.put("telefono", telefono);
        formulario.put("direccion", direccion);
        formulario.put("cp", cp);
        formulario.put("localidad", localidad);
        formulario.put("provincia", provincia);

        Map<String, String> errores = new HashMap<>();

        if (nombre == null || nombre.trim().isEmpty() || apellidos == null || apellidos.trim().isEmpty()) {
            errores.put("general", "Todos los campos obligatorios deben rellenarse.");
        }

        if (nombre != null && !Validaciones.esTextoValido(nombre)) {
            errores.put("nombre", "El nombre solo puede contener letras y espacios.");
        }

        if (apellidos != null && !Validaciones.esTextoValido(apellidos)) {
            errores.put("apellidos", "Los apellidos solo pueden contener letras y espacios.");
        }

        if (telefono != null && !telefono.isEmpty() && !Validaciones.esTelefonoValido(telefono)) {
            errores.put("telefono", "El teléfono debe tener 9 dígitos.");
        }

        if (cp != null && !cp.isEmpty() && !Validaciones.esCPValido(cp)) {
            errores.put("cp", "El código postal debe tener 5 dígitos.");
        }

        if (password != null && !password.trim().isEmpty()) {
            if (currentPassword == null || currentPassword.trim().isEmpty()) {
                errores.put("current_password", "Debe introducir la contraseña actual para cambiarla.");
            } else if (!PasswordHasherArgon2.verifyPassword(usuarioActual.getPassword(), currentPassword.toCharArray())) {
                errores.put("current_password", "La contraseña actual es incorrecta.");
            }
            if (!password.equals(confirmPassword)) {
                errores.put("password", "Las nuevas contraseñas no coinciden.");
            }
            if (!Validaciones.esPasswordSegura(password)) {
                errores.put("passwordSeguridad", "La contraseña nueva debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número.");
            }
        }

        String nuevoNombreAvatar = null;
        Part archivo = request.getPart("avatar");

        if (archivo != null && archivo.getSize() > 0) {
            String mime = archivo.getContentType();
            if (!TIPOS_PERMITIDOS.contains(mime)) {
                errores.put("avatar", "Formato de imagen no permitido. Usa JPG, PNG o WEBP.");
            } else {
                String extension = obtenerExtension(mime);
                nuevoNombreAvatar = UUID.randomUUID().toString() + extension;
            }
        }

        if (!errores.isEmpty()) {
            session.setAttribute("errores", errores);
            session.setAttribute("formulario", formulario);
            response.sendRedirect("UpdateUserServlet");
            return;
        }

        nombre = Validaciones.sanitizarTexto(nombre, 20);
        apellidos = Validaciones.sanitizarTexto(apellidos, 30);
        direccion = Validaciones.sanitizarTexto(direccion, 40);
        localidad = Validaciones.sanitizarTexto(localidad, 40);
        provincia = Validaciones.sanitizarTexto(provincia, 30);

        usuarioActual.setNombre(nombre);
        usuarioActual.setApellidos(apellidos);
        usuarioActual.setTelefono(telefono);
        usuarioActual.setDireccion(direccion);
        usuarioActual.setCodigoPostal(cp);
        usuarioActual.setLocalidad(localidad);
        usuarioActual.setProvincia(provincia);

        if (password != null && !password.trim().isEmpty()) {
            String hashedPassword = PasswordHasherArgon2.hashPassword(password.toCharArray());
            usuarioActual.setPassword(hashedPassword);
        }

        String avatarAntiguo = null;

        if (nuevoNombreAvatar != null) {
            File carpeta = new File(RUTA_UPLOAD);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            Path destino = Paths.get(RUTA_UPLOAD, nuevoNombreAvatar);
            try (InputStream input = archivo.getInputStream()) {
                Files.copy(input, destino, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Nueva imagen subida en: " + destino);
                avatarAntiguo = usuarioActual.getAvatar();
                usuarioActual.setAvatar(nuevoNombreAvatar);
            } catch (Exception e) {
                try {
                    Files.deleteIfExists(destino);
                } catch(Exception ex) {
                    System.err.println("Error borrando el avatar nuevo tras error al guardarlo: " + ex.getMessage());
                }
                errores.put("general", "Ha ocurrido un error al almacenar la nueva imagen");
                session.setAttribute("errores", errores);
                session.setAttribute("formulario", formulario);
                response.sendRedirect("UpdateUserServlet");
                return;
            }
        }

        try {
            if (usuariosDAO.actualizar(usuarioActual)) {
                
                if (avatarAntiguo != null && !"imagen/default.png".equals(avatarAntiguo)) {
                    try {
                        Files.deleteIfExists(Paths.get(RUTA_UPLOAD, avatarAntiguo));
                    } catch (Exception ex) {
                        System.err.println("Error al borrar el avatar antiguo: " + ex.getMessage());
                    }
                }

                if (!usuarioActual.getNombre().equals(userSession.getUsername())) {
                     session.setAttribute("usuario", new UserSessionDTO((long) usuarioActual.getIdUsuario(), usuarioActual.getNombre()));
                }
                session.setAttribute("message", "Datos actualizados correctamente.");
                response.sendRedirect("index");
            } else {

                if (nuevoNombreAvatar != null) {
                    try {
                        Files.deleteIfExists(Paths.get(RUTA_UPLOAD, nuevoNombreAvatar));
                    } catch (Exception ex) {
                         System.err.println("Error borrando avatar nuevo tras fallo de actualización: " + ex.getMessage());
                    }
                }
                errores.put("general", "Error al actualizar los datos.");
                session.setAttribute("errores", errores);
                session.setAttribute("formulario", formulario);
                response.sendRedirect("UpdateUserServlet");
            }
        } catch (Exception e) {
             if (nuevoNombreAvatar != null) {
                 try {
                     Files.deleteIfExists(Paths.get(RUTA_UPLOAD, nuevoNombreAvatar));
                 } catch (Exception ex) {
                      System.err.println("Error borrando avatar nuevo tras fallo de actualización: " + ex.getMessage());
                 }
             }
             errores.put("general", "Error interno: " + e.getMessage());
             session.setAttribute("errores", errores);
             session.setAttribute("formulario", formulario);
             response.sendRedirect("UpdateUserServlet");
             e.printStackTrace();
        }
    }

    private String obtenerExtension(String mime) {
        switch (mime) {
            case "image/jpeg": return ".jpg";
            case "image/png": return ".png";
            case "image/webp": return ".webp";
            default: return "";
        }
    }
}
