package com.mp2i.colloscope;

public class Colle {

    public final String matiere;
    public final String nom;
    public final String creneau;
    public final String code;
    public final String salle;
    //utilisé pour trier les colles
    public int ordre;


    public Colle(String matiere, String nom, String creneau, String code, String salle) {
        this.matiere = matiere;
        this.nom = nom;
        this.creneau = creneau;
        this.code = code;
        this.salle = salle;

        this.parseDate();

    }

    public void parseDate() {
        String[] token = this.creneau.split(" ");
        switch (token[0]) {
            case "Lundi":
                this.ordre = 0;
                break;
            case "Mardi":
                this.ordre = 24;
                break;
            case "Mercredi":
                this.ordre = 24*2;
                break;
            case "Jeudi":
                this.ordre = 24*3;
                break;
            case "Vendredi":
                this.ordre = 24*4;
                break;
        }

        this.ordre += Integer.valueOf(token[1].split("h")[0]);


    }


}
