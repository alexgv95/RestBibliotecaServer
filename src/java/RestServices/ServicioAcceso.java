/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import modelo.Usuario;
import persistencia.ManejadorBBDD;

/**
 * REST Web Service
 *
 * @author Alex
 */
@Path("login")
public class ServicioAcceso {

    @Context
    private UriInfo context;
    ManejadorBBDD DBHandler = new ManejadorBBDD();
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * Creates a new instance of ServicioAcceso
     */
    public ServicioAcceso() {
    }

    /**
     * Retrieves representation of an instance of RestServices.ServicioAcceso
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
     * PUT method for updating or creating an instance of ServicioAcceso
     *
     * @param usuario
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public String acceder(Usuario usuario) {
        String respuesta = "";
        String nombre = usuario.getNombre();
        String password = usuario.getPassword();

        try {
            respuesta = DBHandler.comprobarUsuario(nombre, password);
            if (respuesta.equals("valido")) {
                String token = generarToken(10);
                DBHandler.guardarToken(nombre, password, token);
                return token;
            }
        } catch (Exception ex) {
            System.out.println("El error es: " + ex);
        }
        return respuesta;
    }

    private static String generarToken(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
