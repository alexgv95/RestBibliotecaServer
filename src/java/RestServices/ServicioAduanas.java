/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import modelo.Biblioteca;
import modelo.Libro;
import modelo.Marshaller;
import persistencia.ManejadorBBDD;

/**
 * REST Web Service
 *
 * @author Alex
 */
//@Path("customs")
//public class ServicioAduanas {
//
//    @Context
//    private UriInfo context;
//    ManejadorBBDD DBHandler = new ManejadorBBDD();
//
//    /**
//     * Creates a new instance of ServicioAduanas
//     */
//    public ServicioAduanas() {
//    }
//
//    @GET
//    @NecesidadToken
////    @Consumes(MediaType.TEXT_PLAIN)
////    @Produces(MediaType.TEXT_PLAIN)
//    public String exportarBiblioteca(String nombreFichero, @Context SecurityContext securityContext) {
//        Integer idBiblioteca = getBibliotecaIdPorUsuarioId(securityContext);
//        Biblioteca biblioteca = DBHandler.obtenerBiblioteca(idBiblioteca);
//        Marshaller ms = new Marshaller();
//        ms.marshallerBiblioteca(biblioteca, nombreFichero);
//        File file = new File("./" + nombreFichero + ".xml");
//        return codificadorString(file);
//    }
//
//    @POST
//    @NecesidadToken
////    @Produces(MediaType.APPLICATION_XML)
//    public Biblioteca importarBiblioteca(String nombreFichero, String contenidoFichero, @Context SecurityContext securityContext) {
//        Biblioteca bibliotecaRes = null;
//        Principal user = securityContext.getUserPrincipal();
//        Integer usuarioId = Integer.parseInt(user.getName());
//        
//        File file = descifrarString(contenidoFichero, nombreFichero);
//        Marshaller ms = new Marshaller();
//        Biblioteca bibliotecaXML = ms.unmarshallerBiblioteca(file);
//        List<Libro> librosXML = bibliotecaXML.getLibros();
//        System.out.println(librosXML);
//        try {
//            bibliotecaRes = DBHandler.crearBiblioteca(bibliotecaXML, usuarioId);
//        } catch (Exception ex) {
//            Logger.getLogger(ServicioBiblioteca.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println(ex);
//        }
//        bibliotecaRes.setLibros(librosXML);
//        return bibliotecaRes;
//    }
//
//    public Integer getBibliotecaIdPorUsuarioId(SecurityContext securityContext) {
//        Principal principal = securityContext.getUserPrincipal();
//        Integer usuarioId = Integer.parseInt(principal.getName());
//        return DBHandler.getBibliotecaId(usuarioId);
//    }
//
//    private String codificadorString(File file) {
//        String contenidoFichero = "";
//        String STOP = "#";
//        try {
//            BufferedReader reader;
//            reader = new BufferedReader(new FileReader(file));
//            String line = reader.readLine();
//
//            while (line != null) {
//                contenidoFichero = contenidoFichero + line + STOP;
//                line = reader.readLine();
//            }
//            reader.close();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(ServicioAduanas.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ServicioAduanas.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return contenidoFichero;
//    }
//
//    private File descifrarString(String respuesta, String nombreFichero) {
//        String newRespuesta = respuesta.replaceAll("#", "\n");
//        System.out.println(newRespuesta);
//        BufferedWriter bw = null;
//        File fichero = null;
//        try {
//            fichero = new File("./" + nombreFichero + ".xml");
//            bw = new BufferedWriter(new FileWriter(fichero));
//            bw.write(newRespuesta);
//        } catch (IOException ex) {
//            System.out.println("Excepcion: " + ex);
//        } finally {
//            try {
//                bw.close();
//            } catch (IOException ex) {
//                System.out.println("Excepcion: " + ex);
//            }
//        }
//        return fichero;
//    }
//}
