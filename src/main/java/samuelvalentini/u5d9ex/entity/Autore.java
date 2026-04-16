package samuelvalentini.u5d9ex.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Random;

@Entity
@Table(name = "autori")
public class Autore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "autore_id", nullable = false, updatable = false)
    private long id;

    @Column(name = "autore_nome", nullable = false)
    private String nome;
    @Column(name = "autore_cognome", nullable = false)
    private String cognome;
    @Column(name = "autore_email", nullable = false)
    private String email;
    @Column(name = "autore_data_di_nascita", nullable = false)
    private LocalDate dataDiNascita;
    @Column(name = "autore_avatar", nullable = false)
    private String avatar;

    protected Autore() {
    }

    public Autore(String nome, String cognome, String email, LocalDate dataDiNascita) {
        Random random = new Random();
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.dataDiNascita = dataDiNascita;
        this.avatar = "https://ui-avatars.com/api/?name=" + nome + "+" + cognome;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
