/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import modelo.Usuario;
import persistencia.ManejadorBBDD;

/**
 * REST Web Service
 *
 * @author Alex <agutierrezvivancos@gmail.com>
 */
@Path("signup")
public class ServicioRegistro {

    @Context
    private UriInfo context;
    ManejadorBBDD DBHandler = new ManejadorBBDD();

    /**
     * Creates a new instance of ServicioRegistro
     */
    public ServicioRegistro() {
    }

    /**
     * Retrieves representation of an instance of RestServices.ServicioRegistro
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ServicioRegistro
     *
     * @param usuario
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public String signUp(Usuario usuario) {
        String respuesta = "";
        String nombre = usuario.getNombre();
        String password = usuario.getPassword();

        try {
            respuesta = DBHandler.registrarUsuario(nombre, password);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return respuesta;
    }
}
