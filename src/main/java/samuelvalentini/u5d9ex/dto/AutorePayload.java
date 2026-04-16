package samuelvalentini.u5d9ex.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class AutorePayload {
    @NotNull(message = "Il nome è obbligatorio")
    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 100, message = "Il nome non può superare 100 caratteri")
    private String nome;
    @NotNull(message = "Il cognome è obbligatorio")
    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(max = 100, message = "Il cognome non può superare 100 caratteri")
    private String cognome;
    @Email(message = "L'email non è valida")
    @Size(max = 255, message = "L'email non può superare 255 caratteri")
    @NotNull(message = "L'email è obbligatoria")
    @NotBlank(message = "L'email è obbligatoria")
    private String email;
    @NotNull(message = "La data di nascita è obbligatoria")
    @Past(message = "La data di nascita deve essere nel passato")
    private LocalDate dataDiNascita;

    public AutorePayload(String nome, String cognome, String email, LocalDate dataDiNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.dataDiNascita = dataDiNascita;


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
}
