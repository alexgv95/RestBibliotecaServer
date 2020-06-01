/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

/**
 * REST Web Service
 *
 * @author Alex
 */
@Path("validador")
public class ServicioValidador {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ServicioValidador
     */
    public ServicioValidador() {
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String postValidarXSD(String contenidoXml) {
        String respuesta = "";
        try {
//            Se ha adjuntado el archivo y con estas dos líneas debería poder acceder al mismo
//            sin necesidad de cargarXSD pero al probarlo en otros entornos ha causado problemas.
//            ClassLoader classLoader = getClass().getClassLoader();
//            File xsdFile = new File(classLoader.getResource("biblioteca.xsd").getFile());
            cargarXSD();
            File xsdFile = new File(".   /biblioteca.xsd");
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            Source ficheroXml = new StreamSource(new StringReader(contenidoXml));
            validator.validate(ficheroXml);
            respuesta = ("El fichero es válido");
        } catch (SAXException | IOException ex) {
            respuesta = (" NO es válido");
            System.out.println(ex);
        }
        return respuesta;
    }
    
    private void cargarXSD() {
        File file = new File("./biblioteca.xsd");
        BufferedWriter bW = null;
        if (file.exists() != true) {
            try {
                bW = new BufferedWriter(new FileWriter(file));
            } catch (IOException ex) {
                Logger.getLogger(ServicioValidador.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                bW.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" elementFormDefault=\"qualified\" attributeFormDefault=\"unqualified\">\n"
                        + "       <xs:element name=\"Biblioteca\">\n"
                        + "              <xs:complexType>\n"
                        + "                     <xs:sequence>\n"
                        + "                            <xs:element name=\"facultad\" type=\"xs:string\"></xs:element>\n"
                        + "                            <xs:element name=\"ciudad\" type=\"xs:string\"></xs:element>\n"
                        + "                            <xs:element name=\"ListaLibros\">\n"
                        + "                                   <xs:complexType>\n"
                        + "                                          <xs:sequence>\n"
                        + "                                                 <xs:element name=\"Libro\" maxOccurs=\"unbounded\">\n"
                        + "                                                        <xs:complexType>\n"
                        + "                                                               <xs:sequence>\n"
                        + "                                                                      <xs:element name=\"titulo\" type=\"xs:string\"></xs:element>\n"
                        + "                                                                      <xs:element name=\"autor\" type=\"xs:string\"></xs:element>\n"
                        + "                                                                      <xs:element name=\"numPag\" type=\"xs:int\"></xs:element>\n"
                        + "                                                                  </xs:sequence>\n"
                        + "                                                           </xs:complexType>\n"
                        + "                                                    </xs:element>\n"
                        + "                                             </xs:sequence>\n"
                        + "                                      </xs:complexType>\n"
                        + "                               </xs:element>\n"
                        + "                        </xs:sequence>\n"
                        + "                 </xs:complexType>\n"
                        + "          </xs:element>\n"
                        + "   </xs:schema>");
            } catch (IOException ex) {
                Logger.getLogger(ServicioValidador.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                bW.close();
            } catch (IOException ex) {
                Logger.getLogger(ServicioValidador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
