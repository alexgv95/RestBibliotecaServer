/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Alex
 */
public class Marshaller {

    public void marshallerBiblioteca(Biblioteca biblioteca, String nombreFichero) {
        try {
            JAXBContext jContext = JAXBContext.newInstance(Biblioteca.class);
            javax.xml.bind.Marshaller marshallObj = jContext.createMarshaller();
            marshallObj.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
            OutputStream os = new FileOutputStream("./" + nombreFichero + ".xml");
            marshallObj.marshal(biblioteca, os);
            System.out.println("Biblioteca exportada con exito\n\n");
        } catch (IOException | JAXBException ex) {
            Logger.getLogger(Marshaller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Biblioteca unmarshallerBiblioteca(File fichero) {
        Biblioteca biblioteca = new Biblioteca();
        try {
            JAXBContext jContext = JAXBContext.newInstance(Biblioteca.class);
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            biblioteca = (Biblioteca) unmarshallerObj.unmarshal(fichero);
            System.out.println("Biblioteca importada con exito !");
        } catch (JAXBException ex) {
            Logger.getLogger(Marshaller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return biblioteca;
    }

    public Libro unmarshallerLibro(File file) {
        Libro libro = new Libro();
        try {
            JAXBContext jContext = JAXBContext.newInstance(Libro.class);
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            libro = (Libro) unmarshallerObj.unmarshal(file);
            System.out.println("Libro importado con exito !");
        } catch (JAXBException ex) {
            Logger.getLogger(Marshaller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return libro;
    }

    public void marshallerLibro(Libro libro) {
        try {
            JAXBContext jContext = JAXBContext.newInstance(Libro.class);
            javax.xml.bind.Marshaller marshallObj = jContext.createMarshaller();
            marshallObj.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
            OutputStream os = new FileOutputStream("./" + libro.getTitulo() + ".xml");
            marshallObj.marshal(libro, os);
            System.out.println("Libro exportada con exito\n\n");
        } catch (IOException | JAXBException ex) {
            Logger.getLogger(Marshaller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
