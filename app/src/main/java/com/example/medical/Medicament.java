package com.example.medical;

public class Medicament {


    private String date;

    private String medicaments;

    private String quantite;

    public Medicament(long id, String date, String medicaments, String quantite) {

        this.date = date;
        this.medicaments = medicaments;
        this.quantite = quantite;
    }

    public Medicament() {
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMedicaments(String medicaments) {
        this.medicaments = medicaments;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public String getDate() {
        return date;
    }

    public String getMedicaments() {
        return medicaments;
    }

    public String getQuantite() {
        return quantite;
    }
}
