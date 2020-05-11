/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import modelo.Biblioteca;
import persistencia.ManejadorBBDD;
import java.security.Principal;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import modelo.Libro;
import modelo.ListaLibros;

/**
 * REST Web Service
 *
 * @author Alex
 */
@Path("biblioteca")
public class ServicioBiblioteca {

    @Context
    private UriInfo context;
    ManejadorBBDD DBHandler = new ManejadorBBDD();
    SecurityContext securityContext;

    /**
     * Creates a new instance of ServicioBiblioteca
     */
    public ServicioBiblioteca() {
    }

    /**
     * Retrieves representation of an instance of
     * RestServices.ServicioBiblioteca
     *
     * @param bib
     * @param securityContext
     * @return an instance of java.lang.String
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @NecesidadToken
    public Biblioteca postBiblioteca(Biblioteca bib, @Context SecurityContext securityContext) {
        Biblioteca bibliotecaRes = null;
        Principal user = securityContext.getUserPrincipal();
        Integer usuarioId = Integer.parseInt(user.getName());
        try {
            bibliotecaRes = DBHandler.crearBiblioteca(bib, usuarioId);
        } catch (Exception ex) {
            Logger.getLogger(ServicioBiblioteca.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return bibliotecaRes;
    }

    @GET
    @NecesidadToken
    @Produces(MediaType.APPLICATION_XML)
    public Biblioteca getBiblioteca(@Context SecurityContext securityContext) {
        Integer idBiblioteca = getBibliotecaIdPorUsuarioId(securityContext);
        return DBHandler.obtenerBiblioteca(idBiblioteca);
    }

    public Integer getBibliotecaIdPorUsuarioId(SecurityContext securityContext) {
        Principal principal = securityContext.getUserPrincipal();
        Integer usuarioId = Integer.parseInt(principal.getName());
        return DBHandler.getBibliotecaId(usuarioId);
    }

    @POST
    @Path("/libro")
    @NecesidadToken
    @Consumes(MediaType.APPLICATION_XML)
    public Libro postLibro(Libro libro, @Context SecurityContext securityContext) {
        Libro libroN = null;
        Integer idBiblioteca = getBibliotecaIdPorUsuarioId(securityContext);
        try {
            libroN = DBHandler.crearLibro(libro, idBiblioteca);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return libroN;
    }

    @GET
    @Path("/libro")
    @NecesidadToken
    @Produces(MediaType.APPLICATION_XML)
    public ListaLibros getLibros(@Context SecurityContext securityContext) {
        List<Libro> libros = null;
        Integer idBiblioteca = getBibliotecaIdPorUsuarioId(securityContext);
        try {
            libros = DBHandler.obtenerLibros(idBiblioteca);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return new ListaLibros(libros);
    }

    @GET
    @Path("/libro/{numLibro}")
    @NecesidadToken
    @Produces(MediaType.APPLICATION_XML)
    public Libro getLibro(@PathParam("numLibro") int numLibro, @Context SecurityContext securityContext) {
        Libro libro = null;
        Integer idBiblioteca = getBibliotecaIdPorUsuarioId(securityContext);
        try {
            libro = DBHandler.obtenerLibro(idBiblioteca, numLibro);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return libro;
    }

    @DELETE
    @Path("/libro/{numLibro}")
    @NecesidadToken
    public Biblioteca deleteLibro(@PathParam("numLibro") int numLibro, @Context SecurityContext securityContext) {
        Biblioteca biblioteca = null;
        Integer idBiblioteca = getBibliotecaIdPorUsuarioId(securityContext);
        try {
            biblioteca = DBHandler.borrarLibro(idBiblioteca, numLibro);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return biblioteca;
    }

    @PUT
    @Path("/libro/{numLibro}")
    @NecesidadToken
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Libro putLibro(@PathParam("numLibro") int numLibro, Libro libro, @Context SecurityContext securityContext) {
        Libro libroNuevo = null;
        Integer idBiblioteca = getBibliotecaIdPorUsuarioId(securityContext);
        try {
            libroNuevo = DBHandler.modificarLibro(libro, numLibro, idBiblioteca);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return libroNuevo;
    }

    @GET
    @Path("/libro/texto")
    @NecesidadToken
    @Produces(MediaType.TEXT_PLAIN)
    public String getLibrosTexto(@Context SecurityContext securityContext) {
        String respuesta;
        Integer idBiblioteca = getBibliotecaIdPorUsuarioId(securityContext);
        try {
            respuesta = DBHandler.obtenerBiblioteca(idBiblioteca).toString();
        } catch (Exception ex) {
            respuesta = ex.toString();
        }
        return respuesta;
    }
}
