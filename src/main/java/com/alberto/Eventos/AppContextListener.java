package com.alberto.Eventos;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.alberto.DB.DAOs.CategoriasDAO;

import com.alberto.DB.Factoria.FactoryDAOS;


@WebListener
public class AppContextListener implements ServletContextListener {

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

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        context.removeAttribute("categorias");
        System.out.println("Contexto destruido y categorias eliminadas de application scope");
    }
}
