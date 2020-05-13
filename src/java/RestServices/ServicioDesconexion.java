/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import persistencia.ManejadorBBDD;

/**
 * REST Web Service
 *
 * @author Alex
 */
@Path("logout")
public class ServicioDesconexion {

    @Context
    private UriInfo context;
    ManejadorBBDD DBHandler = new ManejadorBBDD();

    /**
     * Creates a new instance of ServicioDesconexion
     */
    public ServicioDesconexion() {
    }

    @POST
    @NecesidadToken
    public String cerrarSesion(@Context SecurityContext securityContext) {
        String respuesta = "";
        Principal principal = securityContext.getUserPrincipal();
        Integer usuarioId = Integer.parseInt(principal.getName());
        try {
            DBHandler.borrarToken(usuarioId);
            respuesta = "Token borrado";
            return respuesta;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    public Integer getBibliotecaIdPorUsuarioId(SecurityContext securityContext) {
        Principal principal = securityContext.getUserPrincipal();
        Integer usuarioId = Integer.parseInt(principal.getName());
        return DBHandler.getBibliotecaId(usuarioId);
    }
}
