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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import modelo.Biblioteca;
import modelo.Libro;

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
        Statement st = null;
        ResultSet rS = null;

        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            st = conn.createStatement();

            String query = "SELECT * FROM bibliotecas WHERE usuarioId = '" + usuarioId + "';";
            rS = st.executeQuery(query);
            if (rS.next()) {
                Integer bibliotecaId = rS.getInt(1);
                query = "UPDATE bibliotecas SET nombreFacultad = '" + biblioteca.getFacultad()
                        + "' WHERE usuarioId = '" + usuarioId + ";";
                st.executeUpdate(query);
                borrarLibros(bibliotecaId);
                bibliotecaRes = obtenerBiblioteca(bibliotecaId);
                return bibliotecaRes;
            } else {
                query = "INSERT INTO bibliotecas (nombreFacultad, nombreCiudad, usuarioId VALUES"
                        + "('" + biblioteca.getFacultad() + "', '" + biblioteca.getCiudad() + "', "
                        + usuarioId + ");";
                st.executeUpdate(query);
                anadirLinkBiblioteca(biblioteca);
            }
            query = "SELECT bibliotecaId FROM bibliotecas WHERE nombreFacultad  = '" + 
                    biblioteca.getFacultad() + "';";
            rS = st.executeQuery(query);
            int bibliotecaId = 0;
            if (rS.next()){
                bibliotecaId = rS.getInt(1);
            }
            Libro libro = null;
            for (int i = 0; i < biblioteca.contarLibros(); i++) {
                libro = biblioteca.getLibro(i);
                crearLibro(libro, bibliotecaId);
            }
            
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(ManejadorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borrarLibros(Integer idBiblioteca) {
        Biblioteca biblitoeca = null;
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            st = conn.createStatement();
            String query = "DELETE FROM libros WHERE bibliotecaId = " + idBiblioteca + ";";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            liberarRecursos(rS, st, conn);
        }
    }

    public Biblioteca obtenerBiblioteca(int biblitoecaId) {
        Biblioteca biblioteca = null;
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String query = "SELECT * FROM biblitoecas where biblitoecaId =" + biblitoecaId + ";";
            st = conn.createStatement();
            rS = st.executeQuery(query);

            int idBiblioteca = 0;
            String nombreFacultad = "";
            String nombreCiudad = "";
            String linkBiblioteca = "";
            if (rS.next()) {
                idBiblioteca = rS.getInt(1);
                nombreFacultad = rS.getString(2);
                nombreCiudad = rS.getString(3);
                linkBiblioteca = rS.getNString(4);
            }
            biblioteca = new Biblioteca(nombreFacultad, nombreCiudad, linkBiblioteca, idBiblioteca, obtenerLibros(biblitoecaId));
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            liberarRecursos(rS, st, conn);
        }
        return biblioteca;
    }

    public List<Libro> obtenerLibros(int bibliotecaId) {
        List<Libro> listaLibros = new ArrayList();
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String query = "SELECT * FROM libros where bibliotecaId ='" + bibliotecaId + "';";
            st = conn.createStatement();
            rS = st.executeQuery(query);

            while (rS.next()) {
                Libro libro = new Libro(rS.getInt(1), rS.getString(2), rS.getString(3), rS.getInt(4), rS.getString(6));
                listaLibros.add(libro);
                System.out.println(libro.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            liberarRecursos(rS, st, conn);
        }
        return listaLibros;
    }

    private void anadirLinkBiblioteca(Biblioteca biblioteca) {
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();

            String query = "SELECT bibliotecaId FROM bibliotecas where nombreFacultad ='" + biblioteca.getFacultad() + "';";
            st = conn.createStatement();
            rS = st.executeQuery(query);
            int bibliotecaId = 0;
            if (rS.next()) {
                bibliotecaId = rS.getInt(1);
            }

            query = "UPDATE bibliotecas set linkBiblioteca = '" + biblioteca.crearLink(bibliotecaId) + "' WHERE bibliotecaId = " + bibliotecaId + ";";
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {

        } finally {
            liberarRecursos(rS, st, conn);
        }
    }
}
