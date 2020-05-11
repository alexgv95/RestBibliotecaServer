/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex
 * @author Javier
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Biblioteca")
public class Biblioteca implements Serializable {

    @XmlElement
    private String facultad;
    @XmlElement
    private String ciudad;
    @XmlElement(name = "Libro")
    @XmlElementWrapper(name = "Libros")
    private List<Libro> libros = new ArrayList();
    @XmlElement()
    private String linkBiblioteca;
    @XmlElement()
    private Integer idBiblioteca;

    public Biblioteca() {
        this.libros = new ArrayList<>();
    }

    public Biblioteca(String facultad, String ciudad, List<Libro> libros) {
        this.facultad = facultad;
        this.ciudad = ciudad;
        this.libros = libros;
    }

    public Biblioteca(String facultad, String ciudad, String linkBiblioteca, Integer idBiblioteca, List<Libro> libros) {
        this.facultad = facultad;
        this.ciudad = ciudad;
        this.linkBiblioteca = linkBiblioteca;
        this.idBiblioteca = idBiblioteca;
        this.libros = libros;
    }

    public Libro getLibro(int numLibro) {
        return getLibros().get(numLibro);
    }

    public String crearLink(int idBiblioteca) {
        return "http://localhost:8080/RestBibliotecaServer/webresources/biblioteca/";
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public String getLinkBiblioteca() {
        return linkBiblioteca;
    }

    public void setLinkBiblioteca(String linkBiblioteca) {
        this.linkBiblioteca = linkBiblioteca;
    }

    public Integer getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(Integer idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public void anadirLibro(Libro libro) {
        if (getLibros().isEmpty()) {
            libros = new ArrayList<>();
        }
        libros.add(libro);
    }

    public int contarLibros() {
        int numLibros = getLibros().size();
        return numLibros;
    }

    @Override
    public String toString() {
        String respuesta = "";
        respuesta += ("Facultad: " + facultad + "\nCiudad: " + ciudad);
        for (int i = 0; i < libros.size(); i++) {
            respuesta += ("\n Libro " + i + ": " + libros.get(i).toString());
        };
        return respuesta;
    }
}
