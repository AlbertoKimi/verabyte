package com.alberto.Servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import com.alberto.Service.AuthService;
import com.alberto.Model.Usuario;
import com.alberto.Utils.Validaciones;

/**
 * Servlet que gestiona el registro de un nuevo usuario en la aplicación.
 * Permite subir una imagen de avatar, valida la información del formulario
 * y guarda al usuario utilizando {@link AuthService}.
 */
@WebServlet("/RegisterServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 5, 
        maxFileSize = 1024 * 1024 * 20,    
        maxRequestSize = 1024 * 1024 * 30
)
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String RUTA_UPLOAD = System.getProperty("user.home") + File.separator + "app_uploads";

    private static final Set<String> TIPOS_PERMITIDOS = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    /**
     * Procesa peticiones POST desde el formulario de registro.
     * Recoge los datos multipart, valida campos y gestiona la creación del
     * nuevo usuario y el guardado de la imagen correspondiente.
     *
     * @param request  Petición HTTP con datos del formulario.
     * @param response Respuesta HTTP con la redirección adecuada.
     * @throws ServletException En caso de error interno de Servlet (o lectura multipart).
     * @throws IOException      En caso de errores de lectura/escritura de archivos e IO general.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        
        session.removeAttribute("errores");
        session.removeAttribute("formulario");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String nif = request.getParameter("nif");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String cp = request.getParameter("cp");
        String localidad = request.getParameter("localidad");
        String provincia = request.getParameter("provincia");

        java.util.Map<String, String> formulario = new java.util.HashMap<>();
        formulario.put("email", email);
        formulario.put("nombre", nombre);
        formulario.put("apellidos", apellidos);
        formulario.put("nif", nif);
        formulario.put("telefono", telefono);
        formulario.put("direccion", direccion);
        formulario.put("cp", cp);
        formulario.put("localidad", localidad);
        formulario.put("provincia", provincia);

        java.util.Map<String, String> errores = new java.util.HashMap<>();

        // Validaciones
        
        if (nombre == null || nombre.isEmpty() || apellidos == null || apellidos.isEmpty() || 
            password == null || password.isEmpty() || email == null || email.isEmpty()) {
            errores.put("general", "Todos los campos obligatorios deben rellenarse.");
        }

        if (!Validaciones.esTextoValido(nombre)) {
            errores.put("nombre", "El nombre solo puede contener letras y espacios (máx 25 caracteres).");
        }

        if (!Validaciones.esTextoValido(apellidos)) {
            errores.put("apellidos", "Los apellidos solo pueden contener letras y espacios (máx 25 caracteres).");
        }

        if (password != null && !password.equals(confirmPassword)) {
            errores.put("password", "Las contraseñas no coinciden.");
        }

        if (!Validaciones.esTelefonoValido(telefono)) {
            errores.put("telefono", "El teléfono debe tener 9 dígitos.");
        }
        
        if (!Validaciones.esCPValido(cp)) {
            errores.put("cp", "El código postal debe tener 5 dígitos.");
        }

        if (!Validaciones.esPasswordSegura(password)) {
            errores.put("passwordSeguridad", "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número.");
        }

        String nombreArchivoAvatar = "imagen/default.png";
        Part archivo = request.getPart("avatar");

        if (archivo != null && archivo.getSize() > 0) {
            String mime = archivo.getContentType();
            if (!TIPOS_PERMITIDOS.contains(mime)) {
                errores.put("avatar", "Formato de imagen no permitido. Usa JPG, PNG o WEBP.");
            } else {
                String extension = obtenerExtension(mime);
                nombreArchivoAvatar = UUID.randomUUID().toString() + extension;
            }
        }

        if (!errores.isEmpty()) {
            session.setAttribute("errores", errores);
            session.setAttribute("formulario", formulario);
            response.sendRedirect("registro.jsp");
            return;
        }

        if (archivo != null && archivo.getSize() > 0) {
            File carpeta = new File(RUTA_UPLOAD);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            Path destino = Paths.get(RUTA_UPLOAD, nombreArchivoAvatar);
            try (InputStream input = archivo.getInputStream()) {
                Files.copy(input, destino, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Imagen subida en: " + destino);
            } catch (Exception e) {
                try {
                    Files.deleteIfExists(destino);
                } catch(Exception ex) {
                    System.err.println("Error borrando avatar tras error al guardarlo: " + ex.getMessage());
                }
                errores.put("general", "Ha ocurrido un error al almacenar la imagen");
                session.setAttribute("errores", errores);
                session.setAttribute("formulario", formulario);
                response.sendRedirect("registro.jsp");
                return;
            }
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setNif(nif);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setCodigoPostal(cp);
        usuario.setLocalidad(localidad);
        usuario.setProvincia(provincia);
        usuario.setAvatar(nombreArchivoAvatar); 

        AuthService authService = new AuthService();

        try {
            if (authService.registrarUsuario(usuario)) {
                session.setAttribute("message", "Usuario registrado correctamente. Por favor inicia sesión.");
                response.sendRedirect("index");
            } else {
                if (!"imagen/default.png".equals(nombreArchivoAvatar)) {
                    try {
                        Files.deleteIfExists(Paths.get(RUTA_UPLOAD, nombreArchivoAvatar));
                    } catch (Exception ex) {
                        System.err.println("Error borrando avatar tras fallo de registro: " + ex.getMessage());
                    }
                }
                errores.put("general", "Error al registrar el usuario. Puede que el email ya exista.");
                session.setAttribute("errores", errores);
                session.setAttribute("formulario", formulario);
                response.sendRedirect("registro.jsp");
            }
        } catch (Exception e) {
            if (!"imagen/default.png".equals(nombreArchivoAvatar)) {
                try {
                    Files.deleteIfExists(Paths.get(RUTA_UPLOAD, nombreArchivoAvatar));
                } catch (Exception ex) {
                    System.err.println("Error borrando avatar tras excepción de registro: " + ex.getMessage());
                }
            }
            errores.put("general", "Error interno: " + e.getMessage());
            session.setAttribute("errores", errores);
            session.setAttribute("formulario", formulario);
            response.sendRedirect("registro.jsp");
            e.printStackTrace();
        }
    }

    /**
     * Utilidad para obtener la extensión de archivo en formato String dado un tipo MIME de imagen.
     *
     * @param mime El tipo de contenido (ContentType).
     * @return     La extensión (ej: ".jpg"), o cadena vacía si no aplica.
     */
      private String obtenerExtension(String mime) {
        switch (mime) {
            case "image/jpeg": return ".jpg";
            case "image/png": return ".png";
            case "image/webp": return ".webp";
            default: return "";
        }
    }
}