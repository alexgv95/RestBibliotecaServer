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
            System.out.println(rS.toString());
            if (rS.next()) {
                Integer bibliotecaId = rS.getInt(1);
                query = "UPDATE bibliotecas SET nombreFacultad = '" + biblioteca.getFacultad()
                        + "', nombreCiudad = '" + biblioteca.getCiudad() + "' "
                        + "WHERE usuarioId = " + usuarioId + ";";
                st.executeUpdate(query);
                borrarLibros(bibliotecaId);
                bibliotecaRes = obtenerBiblioteca(bibliotecaId);
                return bibliotecaRes;
            } else {
                query = "INSERT INTO bibliotecas (nombreFacultad, nombreCiudad, usuarioId) VALUES"
                        + "('" + biblioteca.getFacultad() + "', '" + biblioteca.getCiudad() + "', "
                        + usuarioId + ");";
                st.executeUpdate(query);
                anadirLinkBiblioteca(biblioteca);
            }
            query = "SELECT bibliotecaId FROM bibliotecas WHERE nombreFacultad  = '"
                    + biblioteca.getFacultad() + "';";
            rS = st.executeQuery(query);
            int bibliotecaId = 0;
            if (rS.next()) {
                bibliotecaId = rS.getInt(1);
            }

//            Libro libro = null;
//            for (int i = 0; i < biblioteca.contarLibros(); i++) {
//                libro = biblioteca.getLibro(i);
//                crearLibro(libro, bibliotecaId);
//            }
            bibliotecaRes = obtenerBiblioteca(bibliotecaId);
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(ManejadorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            liberarRecursos(rS, st, conn);
        }
        return bibliotecaRes;
    }

    public void borrarLibros(Integer idBiblioteca) {
        Biblioteca Biblioteca = null;
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

    public Biblioteca obtenerBiblioteca(int bibliotecaId) {
        Biblioteca biblioteca = null;
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String query = "SELECT * FROM Bibliotecas where BibliotecaId ="
                    + bibliotecaId + ";";
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
                linkBiblioteca = rS.getString(4);
            }
            biblioteca = new Biblioteca(nombreFacultad, nombreCiudad,
                    linkBiblioteca, idBiblioteca, obtenerLibros(bibliotecaId));
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
        int bibliotecaId = 0;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();

            String query = "SELECT bibliotecaId FROM bibliotecas where nombreFacultad ='" + biblioteca.getFacultad() + "';";
            st = conn.createStatement();
            rS = st.executeQuery(query);
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

    public Libro crearLibro(Libro libro, int bibliotecaId) {
        Libro libroN = null;
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String query = "INSERT INTO libros (tituloLibro,autorLibro,numPag,bibliotecaId) "
                    + "VALUES('" + libro.getTitulo() + "','" + libro.getAutor() + "',"
                    + libro.getNumPag() + "," + bibliotecaId + ");";
            st = conn.createStatement();
            st.executeUpdate(query);

            anadirLinkLibro(libro);
            query = "SELECT libroId from libros WHERE tituloLibro ='" + libro.getTitulo() + "';";
            rS = st.executeQuery(query);
            int libroId = 0;
            if (rS.next()) {
                libroId = rS.getInt(1);
            }

            libroN = obtenerLibro(bibliotecaId, libroId);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            liberarRecursos(rS, st, conn);
        }
        return libroN;
    }

    private void anadirLinkLibro(Libro libro) {
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();

            String query = "SELECT libroId, bibliotecaId FROM libros WHERE "
                    + "tituloLibro ='" + libro.getTitulo() + "';";
            st = conn.createStatement();
            rS = st.executeQuery(query);
            int libroId = 0;
            int bibliotecaId = 0;
            if (rS.next()) {
                libroId = rS.getInt(1);
                bibliotecaId = rS.getInt(2);
            }

            query = "UPDATE libros set linkLibro = '" + libro.crearLink(libroId,
                    bibliotecaId) + "' WHERE libroId = " + libroId + ";";
            st.executeUpdate(query);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            liberarRecursos(rS, st, conn);
        }
    }

    public Libro obtenerLibro(int bibliotecaId, int libroId) {
        Libro libro = null;
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String query = "SELECT * FROM libros where bibliotecaId ="
                    + bibliotecaId + " and libroId = " + libroId + ";";
            st = conn.createStatement();
            rS = st.executeQuery(query);
            if (rS.next()) {
                libro = new Libro(rS.getInt(1),
                        rS.getString(2), rS.getString(3), rS.getInt(4),
                        rS.getString(6));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            liberarRecursos(rS, st, conn);
        }
        return libro;
    }

    public Integer getBibliotecaId(Integer usuarioId) {
        Integer bibliotecaId = 0;
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String query = "SELECT bibliotecaId FROM bibliotecas where usuarioId =" + usuarioId + ";";
            st = conn.createStatement();
            rS = st.executeQuery(query);
            if (rS.next()) {
                bibliotecaId = rS.getInt(1);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            liberarRecursos(rS, st, conn);
        }
        return bibliotecaId;
    }

    public Integer comprobarToken(String token) {
        Integer usuarioId = null;
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String query = "SELECT usuarioId FROM usuarios WHERE token ='" + token + "';";
            //System.out.println(query);
            st = conn.createStatement();
            rS = st.executeQuery(query);
            if (rS.next()) {
                usuarioId = rS.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            liberarRecursos(rS, st, conn);
        }
        return usuarioId;
    }

    public Biblioteca borrarLibro(int bibliotecaId, int libroId) {
        Biblioteca biblioteca = null;
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String query = "DELETE FROM libros WHERE bibliotecaId =" + bibliotecaId
                    + " and libroId = " + libroId + ";";
            st = conn.createStatement();
            st.executeUpdate(query);
            biblioteca = obtenerBiblioteca(bibliotecaId);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            liberarRecursos(rS, st, conn);
        }
        return biblioteca;
    }

    public Libro modificarLibro(Libro libro, int libroId, int bibliotecaId) {
        Libro libroNuevo = null;
        Connection conn = null;
        Statement st = null;
        ResultSet rS = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/biblioDatasource");
            conn = dataSource.getConnection();
            String query = "UPDATE libros SET tituloLibro ='" + libro.getTitulo()
                    + "', autorLibro='" + libro.getAutor() + "', numPag = "
                    + libro.getNumPag() + " WHERE libroId =" + libroId + " "
                    + "AND bibliotecaId = " + bibliotecaId + ";";
            st = conn.createStatement();
            st.executeUpdate(query);

            anadirLinkLibro(libro);
            libroNuevo = obtenerLibro(bibliotecaId, libroId);
        } catch (Exception ex) {

        } finally {
            liberarRecursos(rS, st, conn);
        }
        return libroNuevo;
    }
}
