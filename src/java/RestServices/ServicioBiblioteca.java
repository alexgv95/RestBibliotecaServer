/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices;

import java.security.Principal;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import modelo.Biblioteca;
import persistencia.ManejadorBBDD;

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
    SecurityContext sC;
    /**
     * Creates a new instance of ServicioBiblioteca
     */
    public ServicioBiblioteca() {
    }

    /**
     * Retrieves representation of an instance of RestServices.ServicioBiblioteca
     * @param biblioteca
     * @param sC
     * @return an instance of java.lang.String
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @NecesidadToken
    public Biblioteca postBiblioteca(Biblioteca biblioteca, @Context SecurityContext sC) {
        Biblioteca bibliotecaRes = null;
        Principal principal = sC.getUserPrincipal();
        Integer usuarioId = Integer.parseInt(principal.getName());
        try {
            bibliotecaRes = DBHandler.crearBiblioteca(biblioteca, usuarioId);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return bibliotecaRes;
    }
    /**
     * PUT method for updating or creating an instance of ServicioBiblioteca
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
