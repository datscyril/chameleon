package pTreatments;

public class Player {
    private String Pseudo;
    private int couleur;
    private boolean IA;//True si IA 

    public Player(String pseudo, int couleur, boolean IA) {
        Pseudo = pseudo;
        this.couleur = couleur;
        this.IA = IA;
    }

    public String getPseudo() {
        return Pseudo;
    }

    public int getColor() {
        return couleur;
    }

    public boolean isAI() {
        return IA;
    }



    public void setIA(boolean IA) {
        this.IA = IA;
    }

    public void setPseudo(String Pseudo) {
        this.Pseudo = Pseudo;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }
    
    


    @Override
    public String toString() {
        return "Joueur{" +
                "Pseudo='" + Pseudo + '\'' +
                '}';
    }

}
