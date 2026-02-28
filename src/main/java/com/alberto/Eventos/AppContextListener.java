package com.alberto.Eventos;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.alberto.DB.DAOs.CategoriasDAO;

import com.alberto.DB.Factoria.FactoryDAOS;


/**
 * Listener de contexto de la aplicación web.
 * Intercepta los eventos de inicio y apagado del servidor web o despliegue.
 * Se encarga de cargar las categorías en memoria (`application scope`) al inicio.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    /**
     * Evento disparado al inicializar la aplicación.
     * Carga de la BD la lista de categorías y la añade a los atributos globales de la aplicación.
     *
     * @param servletContextEvent Evento de contexto provisto por el contenedor.
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        
        ServletContext context = servletContextEvent.getServletContext();
        try {
            if (context.getAttribute("categorias") == null) {
                CategoriasDAO categoriasDAO = FactoryDAOS.crearCategoriasDAO();
                context.setAttribute("categorias", categoriasDAO.listarTodas());
            }
            System.out.println("Contexto inicializado y categorias cargadas en application scope");
        } catch (Exception e) {
            System.err.println("Error al cargar las categorias en el inicio del contexto: " + e.getMessage());
            
        }
    }

    /**
     * Evento disparado al apagar o reiniciar la aplicación web.
     * Limpia los atributos alojados en la memoria global.
     *
     * @param servletContextEvent Evento de contexto provisto por el contenedor.
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        context.removeAttribute("categorias");
        System.out.println("Contexto destruido y categorias eliminadas de application scope");
    }
}
