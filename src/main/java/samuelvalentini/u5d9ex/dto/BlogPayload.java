package samuelvalentini.u5d9ex.dto;


import samuelvalentini.u5d9ex.enumeration.Categoria;

public class BlogPayload {
    Categoria categoria;
    String titolo;
    String contenuto;
    Long autoreId;

    public BlogPayload(Categoria categoria, String titolo, String contenuto, Long autoreId) {
        this.categoria = categoria;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.autoreId = autoreId;
    }

    public Long getAutoreId() {
        return autoreId;
    }

    public void setAutoreId(Long autoreId) {
        this.autoreId = autoreId;
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

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }
}
