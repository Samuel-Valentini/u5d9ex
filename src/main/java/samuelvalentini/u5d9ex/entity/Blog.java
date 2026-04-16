package samuelvalentini.u5d9ex.entity;

import jakarta.persistence.*;
import samuelvalentini.u5d9ex.enumeration.Categoria;

import java.util.Random;

@Entity
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id", nullable = false, updatable = false)
    private long id;
    @Column(name = "blog_categoria", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @Column(name = "blog_titolo", nullable = false)
    private String titolo;
    @Column(name = "blog_cover", nullable = false)
    private String cover;
    @Column(name = "blog_contenuto", nullable = false, columnDefinition = "TEXT")
    private String contenuto;
    @Column(name = "blog_tempo_di_lettura", nullable = false)
    private int tempoDiLettura;
    @ManyToOne(optional = false)
    @JoinColumn(name = "autore_id", nullable = false)
    private Autore autore;

    protected Blog() {
    }

    public Blog(Categoria categoria, String titolo, String contenuto, Autore autore) {
        Random random = new Random();
        this.categoria = categoria;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.autore = autore;
        this.cover = "https://picsum.photos/200/300";
        //stimo il tempo di lettura in 200 parole al minuto
        this.tempoDiLettura = (int) Math.ceil((double) (contenuto.trim().split("\\s+").length) / 200);
    }

    public long getId() {
        return id;
    }

    public Autore getAutore() {
        return autore;
    }

    public void setAutore(Autore autore) {
        this.autore = autore;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public int getTempoDiLettura() {
        return tempoDiLettura;
    }

    public void setTempoDiLettura(int tempoDiLettura) {
        this.tempoDiLettura = tempoDiLettura;
    }
}
