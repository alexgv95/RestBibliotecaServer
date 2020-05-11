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

    @Override
    public String toString() {
        String respuesta = "";
        for (int i = 0; i < libros.size(); i++) {
            respuesta += ("\n Libro [" + libros.get(i).getIdLibro() + "]:    "
                    + "\n\tTitulo: " + libros.get(i).getTitulo() + "    "
                    + "\n\tAutor : " + libros.get(i).getAutor() + "    "
                    + "\n\tNºPags: " + libros.get(i).getNumPag());

        };
        return respuesta;
    }

}
