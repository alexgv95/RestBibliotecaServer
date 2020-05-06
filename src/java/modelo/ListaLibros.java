/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Libros")
public class ListaLibros {

    @XmlElement(name = "Libro")
    private List<Libro> libros = new ArrayList();

    public ListaLibros() {
    }

    public ListaLibros(List<Libro> lLibros) {
        this.libros = lLibros;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    //ECHAR UN VISTAZO AL TOSTRING DE LISTA LIBROS
    @Override
    public String toString() {
        return "ListaLibros{" + "libros=" + libros + '}';
    }

}
