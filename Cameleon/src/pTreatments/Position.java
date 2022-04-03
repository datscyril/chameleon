package pTreatments;

public class Position {
    int x; //Coordonnées de la position dans la grille
    int y;
    double mutable=0;//Nombre de pions adverses mutables via cette position
    double risk=1;//Valeur représentative au risque qu'effectuer un mouvement à cette position
    double ratio=0;//Ratio gain/risque d'un mouvement à cette position

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getRisk() {
        return risk;
    }

    public void setRisk(double risk) {
        this.risk = risk;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
    
    public double getMutable() {
        return mutable;
    }

    public void setMutable(double mutable) {
        this.mutable = mutable;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
