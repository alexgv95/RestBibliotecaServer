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
import modelo.Biblioteca;

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
        String respuesta = "no valido";
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

    public void guardarToken(String usuario, String password, String token) {
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String usuarioId = getUsuarioId(usuario, password);
            String query = "UPDATE usuarios SET token = '" + token + "' WHERE usuarioId = " + usuarioId + ";";
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            liberarRecursos(rS, st, conn);
        }
    }

    public String getUsuarioId(String usuario, String password) {
        String usuarioId = "";
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String query = "SELECT usuarioId FROM usuarios WHERE nombre ='"
                    + usuario + "' AND password = sha1('" + password + "');";
            st = conn.createStatement();
            rS = st.executeQuery(query);

            if (rS.next()) {
                usuarioId = rS.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            liberarRecursos(rS, st, conn);
        }
        return usuarioId;
    }

    public Biblioteca crearBiblioteca(Biblioteca biblioteca, Integer usuarioId) {
        Biblioteca bibliotecaRes = null;
        Connection conn = null;
        Statement st;
        ResultSet
    }
}
