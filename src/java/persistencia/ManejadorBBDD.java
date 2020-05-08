/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Alex <agutierrezvivancos@gmail.com>
 */
public class ManejadorBBDD {

    public String registrarUsuario(String nombre, String password) {
        String respuesta = "";
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String query = "SELECT * FROM usuarios WHERE nombre='" + nombre + "' "
                    + "and password=sha1('" + password + "');";
            st = conn.createStatement();
            rS = st.executeQuery(query);
            if (rS.next()) {
                respuesta = "Usuario existente";
            } else {
                query = "INSERT INTO usuarios(nombre,password) VALUES('" + nombre
                        + "',sha1('" + password + "'));";
                st.executeUpdate(query);
                respuesta = "Se ha registra el usuario con éxito !";
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            liberarRecursos(rS, st, conn);
        }
        return respuesta;
    }

    public static void liberarRecursos(ResultSet rS, Statement st, Connection conn) {
        if (rS != null) {
            try {
                rS.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    public String comprobarUsuario(String usuario, String password) {
        String respuesta = "";
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String query = "SELECT * FROM USUARIOS WHERE nombre='" + usuario
                    + "' and password = sha1('" + password + "');";
            st = conn.createStatement();
            rS = st.executeQuery(query);
            if (rS.next()) {
                respuesta = "valido";
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(ManejadorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            liberarRecursos(rS, st, conn);
        }
        return respuesta;
    }
}
