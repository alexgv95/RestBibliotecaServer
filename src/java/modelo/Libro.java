/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lord_Alex
 * @author Darth_Javier
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Libro")
public class Libro implements Serializable {

    @XmlElement
    private String titulo;
    @XmlElement
    private String autor;
    @XmlElement
    private Integer numPag;
    @XmlElement
    private Integer idLibro;
    @XmlElement
    private String linkLibro;

    public Libro() {

    }

    public Libro(String titulo, String autor, Integer numPag) {
        this.titulo = titulo;
        this.autor = autor;
        this.numPag = numPag;
    }

    public Libro(Integer idLibro, String titulo, String autor, Integer numPag, String linkLibro) {
        this.titulo = titulo;
        this.autor = autor;
        this.numPag = numPag;
        this.idLibro = idLibro;
        this.linkLibro = linkLibro;
    }

    public String crearLink(int idLibro, int idBiblioteca) {
        return "http://localhost:8080/RestBibliotecaServer/webresources/biblioteca/libro/" + idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getNumPag() {
        return numPag;
    }

    public void setNumPag(Integer numPag) {
        this.numPag = numPag;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public String getLinkLibro() {
        return linkLibro;
    }

    public void setLinkLibro(String linkLibro) {
        this.linkLibro = linkLibro;
    }

    @Override
    public String toString() {
        return "Libro{" + "titulo=" + titulo + ", autor=" + autor + ", numPag=" + numPag + ", idLibro=" + idLibro + ", linkLibro=" + linkLibro + '}';
    }

}
