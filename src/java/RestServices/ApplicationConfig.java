/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Alex
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(RestServices.FiltroAutentificacion.class);
        resources.add(RestServices.ServicioAcceso.class);
        resources.add(RestServices.ServicioBiblioteca.class);
        resources.add(RestServices.ServicioDesconexion.class);
        resources.add(RestServices.ServicioRegistro.class);
        resources.add(RestServices.ServicioValidador.class);
    }
    
}
