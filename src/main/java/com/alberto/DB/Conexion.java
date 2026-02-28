package com.alberto.DB;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Conexion {
    private static DataSource dataSource;

    static {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup(
                "java:/comp/env/jdbc/MySQLDS"
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener DataSource", e);
        }
    }

    public static Connection getConexion() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("Fallo al obtener la conexion a la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo establecer conexion con la base de datos.", e);
        }
    }
}