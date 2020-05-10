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
        System.out.println(user);
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
        Integer numBiblioteca = getBibliotecaIdPorUsuarioId(securityContext);
        return DBHandler.obtenerBiblioteca(numBiblioteca);
    }

    public Integer getBibliotecaIdPorUsuarioId(SecurityContext securityContext) {
        Principal principal = securityContext.getUserPrincipal();
        Integer usuarioId = Integer.parseInt(principal.getName());
        return DBHandler.getBibliotecaId(usuarioId);
    }
}
