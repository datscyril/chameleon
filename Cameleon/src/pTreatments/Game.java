package pTreatments;

import java.awt.Color;
import java.util.ArrayList;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {

    //VALEUR CONSTANTES DU TABLEAU
    private final int MIN_LENGTH_GRID = 0;
    private final int MAX_LENGTH_GRID = 9;
    private final int MIN_PROHIBITED = 3;
    private final int MAX_PROHIBITED = 6;

    //VALEURS CONSTANTES DE LA GRILLE
    private final int EMPTY = 0; // EMPTY CELL. Cases vides de la grille
    private final int PROHIBITED = -1; // Cases interdites
    private final int RED = 1;
    private final int BLUE = 2;
    private final int YELLOW = 3;
    private final int GREEN = 4;

    //VALEUR CONSTANTE DE DEPLACEMENT
    private final int JUMP_LENGTH = 2;
    private final int DUPLICATION_LENGTH = 1;

//VALEUR CONSTANTE DE JEU
    private final boolean HARD = true;
    private final boolean EASY = false;

    //Création des attributs qui définissent le jeu. Le jeu se compose d'une grid, et de deux joueurs
    private int[][] grid = new int[MAX_LENGTH_GRID][MAX_LENGTH_GRID];
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private boolean Difficulty = false;

    public void setDifficulty(boolean difficulty) {
        this.Difficulty = difficulty;
    }

    public boolean isDifficulty() {
        return Difficulty;
    }
    

    //Constructeur
    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;

        //On choisit aléatoirement le premier joueur qui joue
        this.currentPlayer = pickTheFirstPlayer();
        newGrid();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    public boolean isCurrentPlayer(Player player){
        return currentPlayer == player;
    }

    public boolean isThereAnAI() {
        return p1.isAI() || p2.isAI();
    }

    public void newGrid() { //Méthode pour initialiser la grille de jeu
        // J'initialise la grid à 0, 0 = vide.
        for (int i = MIN_LENGTH_GRID; i < MAX_LENGTH_GRID; i++) {
            for (int j = MIN_LENGTH_GRID; j < MAX_LENGTH_GRID; j++) {
                grid[i][j] = EMPTY;
            }
        }

        //Je place les trous
        for (int i = MIN_PROHIBITED; i < MAX_PROHIBITED; i++) {
            for (int j = MIN_PROHIBITED; j < MAX_PROHIBITED; j++) {
                grid[i][j] = PROHIBITED; //-1 = trou
            }
        }
        //Je place les cases du joueur 1
        grid[0][0] = p1.getColor();
        grid[8][8] = p1.getColor();
        //Je place les cases du joueur 2
        grid[0][8] = p2.getColor();
        grid[8][0] = p2.getColor();

    }

    // méthode temporaire d'affichage de la grille pour effectuer nos tests. Ne pas en tenir compte
    public void afficherGrille() { // méthode temporaire d'affichage de la grille ne pas en tenir compte

        for (int i = 0; i < MAX_LENGTH_GRID; i++) {
            for (int j = 0; j < MAX_LENGTH_GRID; j++) {
                switch (grid[i][j]) {
                    case PROHIBITED:
                        System.out.print("     ");
                        break;
                    case EMPTY:
                        // Si les cases de ma grid sont vides
                        System.out.print("  " + "." + "  ");
                        break;
                    case 1:
                        System.out.print("  " + "1" + "  ");
                        break;
                    case 2:
                        System.out.print("  " + "2" + "  ");
                        break;
                    default:
                        System.out.print("  " + grid[i][j] + "  ");
                        break;
                }

            }
            System.out.println("");
        }
    }

    public void afficherGrille(FButton bout[][]) { // méthode temporaire d'affichage de la grille ne pas en tenir compte

        for (int i = MIN_LENGTH_GRID; i < MAX_LENGTH_GRID; i++) {
            for (int j = MIN_LENGTH_GRID; j < MAX_LENGTH_GRID; j++) {
                switch (grid[i][j]) {
                    case PROHIBITED:

                        bout[i][j].setVisible(false);
                        break;
                    case EMPTY:
                        // Si les cases de ma grid sont vides
                        bout[i][j].setBackground(Color.white);
                        break;
                    case RED:
                        bout[i][j].setBackground(Color.red);
                        break;
                    case YELLOW:
                        bout[i][j].setBackground(Color.yellow);
                        break;
                    case BLUE:
                        bout[i][j].setBackground(Color.blue);
                        break;
                    case GREEN:
                        bout[i][j].setBackground(Color.green);
                        break;
                    default:
                        break;
                }
            }
//            Player otherPlayer;
//            if (currentPlayer == p2) {
//                otherPlayer = p1;
//            } else {
//                otherPlayer = p2;
//            }
//            ArrayList<Position> playerPosition;
//            if (isThereAnAI() == true) {
//                playerPosition = getListPosition(currentPlayer);
//            } else {
//                playerPosition = getListPosition(otherPlayer);
//            }
//
//            for (Position p : playerPosition) {
//
//
//            }
        }

    }

    public void displayPossibleMove(FButton bout[][], Position p) {
        int x = p.getX();
        int y = p.getY();
        for (int k = x - JUMP_LENGTH; k <= x + JUMP_LENGTH; k++) {
            for (int l = y - JUMP_LENGTH; l <= y + JUMP_LENGTH; l++) {
                if (isInGrid(k, l) && grid[k][l] == EMPTY && currentPlayer.isAI() == false) {
                    bout[k][l].setBackground(Color.GRAY);

                }
            }
        }

    }

    public void assignPlayer() {
        //methode qui designe le joueur dont c'est le tour à chaque fois.
        if (currentPlayer == p1) {
            currentPlayer = p2;
        } else {
            currentPlayer = p1;
        }
    }

    public void moveIA() {//methode responsable des mouvements de l'IA, elle constitue l'IA et sa stratégie
        //Déclaration des variables nécessaires
        int xPion = 0;//Coordonnees du pion etudie
        int yPion = 0;
        int randomindList = 0; //Indice genere aleatoirement
        double EmptyPawnCounter = 0;//Compteur de pions adverses autour d'une case vide donnee
        double bestRatioPawn = 0;//Meilleur ratio gain/risque obtenu à l'echelle d'un pion
        double bestRatioGrid = 0;//Meilleur ratio gain/risque obtenu à l'echelle de tous les pions du joueur
        ArrayList<Position> computerPosition = getListPosition(currentPlayer);
        //Liste des pions de l'ia, on la récupere grace a la methode getListPosition.
        //Cette liste nous permet d'étudier chaque position du joueur
        ArrayList<Position> computerDestination = new ArrayList<>();
        //Liste des positions(cases vides) autour duquelles un pion peut se dupliquer ou bien sauter.
        ArrayList<Position> computerbestDestination = new ArrayList<>();
        //Liste de positions de meme taille que la liste des pions du joueur
        // qui contient pour chaque pion la position la plus avantageuse à jouer

        //Etude
        /**
         * PETIT COMMUNIQUE DE STRATEGIE Nous avons décidé, pour le joueur
         * ordinateur d'examiner toutes les possibilites de jeux possibles. Nous
         * conservons uniquement les meilleurs, et si deux mouvement sont
         * exaequo en terme de gain/perte, nous en choisissons un au hasard
         * parmis ceux possibles. Ce qui laisse à l'ordinateur un coté plus
         * imprévisible. Pour ce qui est du choix des meilleurs positions, nous
         * avions défini un facteur gain/risque. Ce facteur permet à
         * l'ordinateur de choisir la stratégie la plus pertinente à savoir, un
         * gain maximal pour le moins de perte possible, autrement dit ; Cela
         * laisse à l'ordinateur un champ de possibilité pour ce qui est de
         * jouer offensivement ou défensivement Si le joueur ordinateur joue
         * offensivement uniquement, il court droit au suicide, S'il joue
         * défensivement uniquement, il n'a pas pour objectif de gagner la
         * partie Le gain est évalué en fonction du nombre de mutables possible,
         * Le risque correspond à la possibilité de se faire muter si l'on joue
         * sur une case donnée. Nous espérons que cette solution saura vous
         * satisfaire. Max & Cyril Co.
         */
        for (Position i : computerPosition) { //On parcours la liste des pions du joueur
            xPion = i.getX();//On recupere les coordonnees du pion
            yPion = i.getY();
            for (int k = xPion - JUMP_LENGTH; k <= xPion + JUMP_LENGTH; k++) { // On étudie les cases jouables autour de la position IA
                for (int l = yPion - JUMP_LENGTH; l <= yPion + JUMP_LENGTH; l++) {
                    if (isInGrid(k, l) && (grid[k][l] == 0)) {//Si la case est dans la grille et est vide,
                        // on va compter le nombre de pions adverses que l'on pourra muter si on joue sur cette case
                        Position d = new Position(k, l);//

                        if (Difficulty == HARD) {
                            if (distanceCalculator(i, d) == DUPLICATION_LENGTH) {
                                //Cette verification a pour but de donner l'avantage à la duplication en comparaison au saut:
                                //En effet un saut libère une case où potentiellement il est possible que l'ennemie y joue
                                // et par conséquent, récupère l'avantage.

                                EmptyPawnCounter = 1;
                            }
                        }
                        EmptyPawnCounter = EmptyPawnCounter + countOpponentPawn(k, l, DUPLICATION_LENGTH); // on compte
                        d.setMutable(EmptyPawnCounter);
                        //On attribue à la case vide etudiee son nombre de pions adverses mutables
                        EmptyPawnCounter = 0;//On reinitialise le compteur;
                        if (countOpponentPawn(k, l, JUMP_LENGTH + 1) != 0) {

                            //Si un pion adverse est present a une distance de 3 de la case vide étudié,
                            // il est susceptible de sauter et de muter les pions de notre IA au tour d'apres,
                            // dans ce cas on attribue un risque de 6/8 a la case etudie
                            // Ce chiffre est modulable, il sera ajusté si nécessaire
                            //6 si le joueur adverse se trouve à 3 cases de la position
                            // (ne sera pas mute et aura des chances d'intervenir)
                            d.setRisk(6);
                        }
                        if (countOpponentPawn(k, l, JUMP_LENGTH) != 0) {
                            //Si un pion adverse est present a une distance de 2 de la case vide étudié,
                            // il est susceptible de se dupliquer et de muter les pions de l'IA au tour d'apres,
                            // dans ce cas on attribue un risque de 8/8 a la case etudie
                            // Ce chiffre est modulable, il sera ajusté si nécessaire
                            //8 si le joueur adverse se trouve à 2 cases de la position
                            // (ne sera pas mute et aura bcp de chance d'intervenir)

                            d.setRisk(8);

                        }

                        //On définit le ratio gain risque autour de cette case vide étudié et on l'ajoute à la liste des
                        //mouvements pour une position de l'ia étudié
                        d.setRatio((d.getMutable() / d.getRisk()));
                        computerDestination.add(d);
                    }
                }
            }
            for (Position n : computerDestination) {
                //Pour chaque mouvement enregistré
                //On retient la plus grande valeur parmis les ratio des cases vides etudiees
                if (n.getRatio() > bestRatioPawn) {
                    bestRatioPawn = n.getRatio();
                }
            }
            i.setRatio(bestRatioPawn);//On attribue cette valeur à la position de l'IA étudié directement comme reference

            //On souhaite retenir le meilleur déplacement à jouer pour une case vide donnée.
            if (computerDestination.isEmpty()) {
                //Si il n'y a pas de positions jouables pour un pion donne,
                // on ajoute une position aux attribus nuls comme case la plus benefique a jouer
                Position pDefault = new Position(0, 0);
                //De cette maniere on ecarte la problematique des pions ne pouvant se deplacer,
                // qui ne seront alors jamais designer des lors que le mouvement est possible autre part.
                // Si il ne l'es pas, la méthode moveIA ne sera jamais lancé.
                computerbestDestination.add(pDefault);

            } else {//Sinon, on tire aléatoirement parmis les cases ayant le meilleur ratio
                do {
                    randomindList = (int) ((Math.random()) * (computerDestination.size()));//tirage aleatoire d'un indice
                } while (computerDestination.get(randomindList).getRatio() != bestRatioPawn);
                //si l'element de la liste a cet indice fait parti des plus benefique a jouer on le garde,
                // on a recours a l'aleatoire afin de rendre plus imprevisible notre IA,
                // si on parcourait la liste et que l'on prenait le premier element ayant le meilleur ratio,
                // l'IA aurait une tendance a jouer en haut a gauche

                computerbestDestination.add(computerDestination.get(randomindList));
                //On ajoute cette position comme la position qui sera joué si le pion est choisi
            }

            computerDestination.clear();
            //On vide la liste des positions jouable pour un pion donne avant de passer au pion suivant

            //On souhaite retenir le meilleur ratio parmi tout les positions de l'ia étudié
            if (bestRatioPawn >= bestRatioGrid) {
                bestRatioGrid = bestRatioPawn;
            }
            bestRatioPawn = 0;

        }
        //mouvement
        do {
            randomindList = (int) ((Math.random()) * (computerPosition.size()));
        } while (computerPosition.get(randomindList).getRatio() != bestRatioGrid);
        //On tire aleatoirement parmis les positions de l'IA ayant le meilleur ratio à jouer,
        // à noter que dans cette methode on est sur que la liste des positions n'est pas vide (cf cond de fin)

        move(computerPosition.get(randomindList), computerbestDestination.get(randomindList));
        //On effectue le mouvement astucieux

    }

    public void move(Position pawn, Position destination) {
        //Methode realisant le mouvement d'un pion vers une case vide a partir de leur deux positions
        int x = pawn.getX();
        int y = pawn.getY();
        int a = destination.getX();
        int b = destination.getY();
        if (isInGrid(a, b) == true && grid[a][b] == EMPTY && grid[x][y] == currentPlayer.getColor()) {
            if (distanceCalculator(pawn, destination) == DUPLICATION_LENGTH) {
                grid[a][b] = grid[x][y];
                mutatePawn(a, b);

            } else if (distanceCalculator(pawn, destination) == JUMP_LENGTH) {
                grid[a][b] = grid[x][y];
                grid[x][y] = EMPTY;
                mutatePawn(a, b);
            }

        }
    }

    public boolean isMovePossible(Position pawn, Position destination) {
        // Methode pour vérifier si un mouvement est possible
        int x = pawn.getX();
        int y = pawn.getY();
        int a = destination.getX();
        int b = destination.getY();
        if (isInGrid(a, b) == true && grid[a][b] == EMPTY && grid[x][y] == currentPlayer.getColor()) {
            if (distanceCalculator(pawn, destination) == DUPLICATION_LENGTH) {
                return true;
            } else if (distanceCalculator(pawn, destination) == JUMP_LENGTH) {
                return true;
            }
        }
        return false;
    }

    private void mutatePawn(int x, int y) {
        //Methode pour muter les pions autour d'une position
        for (int i = x - DUPLICATION_LENGTH; i <= x + DUPLICATION_LENGTH; i++) {
            for (int j = y - DUPLICATION_LENGTH; j <= y + DUPLICATION_LENGTH; j++) {
                if (isInGrid(i, j)) {
                    if (i == x && j == y) {
                    } else if (grid[i][j] > EMPTY && grid[i][j] != grid[x][y]) {
                        grid[i][j] = grid[x][y];
                    }
                }
            }
        }
    }

    public boolean isEnd() {//Methode renvoyant vraie si la partie est finie et faux si elle est encore en cours
        for (int i = MIN_LENGTH_GRID; i < MAX_LENGTH_GRID; i++) {
            for (int j = MIN_LENGTH_GRID; j < MAX_LENGTH_GRID; j++) {
                if (countPawns(p1) == 0 || countPawns(p2) == 0) {//Fin si l'un des joeur n'a plus de pions
                    return true;
                } else if (countEmpty() == 0) {//Fin si la grille est pleine
                    return true;
                } else if (isMovePossibleForPlayer(p1) == false || isMovePossibleForPlayer(p2) == false) {

                    //Fin si l'un des joueurs ne peux plus jouer
                    return true;
                }
            }
        }
        return false;//Sinon la partie continue
    }

    public int countPawns(Player p) {//Methode comptant les pions d'un joueur donne
        int pions = 0;
        for (int i = MIN_LENGTH_GRID; i < MAX_LENGTH_GRID; i++) {
            for (int j = MIN_LENGTH_GRID; j < MAX_LENGTH_GRID; j++) {
                if (grid[i][j] == p.getColor()) {
                    pions++;
                }
            }
        }

        return pions;
    }

    private int countEmpty() {//Methode comptant les cases vides presentes dans la grille
        int nbEmpty = 0;
        for (int i = MIN_LENGTH_GRID; i < MAX_LENGTH_GRID; i++) {
            for (int j = MIN_LENGTH_GRID; j < MAX_LENGTH_GRID; j++) {
                if (grid[i][j] == EMPTY) {
                    nbEmpty++;
                }
            }
        }
        return nbEmpty;
    }

    private int countOpponentPawn(int x, int y, int degree) {
        //Methode comptant les pions adverses autour d'une case donnée et ce à une distance donnée
        int counterOpp = 0;
        Position p = new Position(x, y);

        for (int i = x - degree; i <= x + degree; i++) {
            //On parcours le carré centré sur le pion et de coté 2 fois la distance d'etude voulue
            for (int j = y - degree; j <= y + degree; j++) {
                Position d = new Position(i, j);
                if (isInGrid(i, j)) {//Si la case etudie est dans la grille

                    if (distanceCalculator(p, d) == degree) {//a la distance d'etude souhaitee

                        if (grid[i][j] == getOtherPlayerColor()) {//et de la couleur de l'adversaire
                            counterOpp++;//On incremente le compteur
                        }
                    }
                }
            }
        }
        return counterOpp;
    }

    private int getOtherPlayerColor() {//Methode renvoyant la couleur du joueur adverse sous la forme d'un entier
        Player pCurrent = getCurrentPlayer();
        if (pCurrent == p1) {
            return p2.getColor();
        } else if (getCurrentPlayer() == p1) {
            return p1.getColor();
        } else {
            Exception otherPlayerException = new Exception();
            otherPlayerException.getMessage();
            //On creer une exception si la couleur du joueur dont c'est le tour n'est pas une couleur possible
            return 0;
        }

    }

    public int countArroundPosition(int k, int l) {//Methode comptant les pions d'un joueur donne
        int pions = 0;
        for (int i = k - DUPLICATION_LENGTH; i <= k + DUPLICATION_LENGTH; i++) {
            for (int j = l - DUPLICATION_LENGTH; j <= l + DUPLICATION_LENGTH; j++) {
                if (isInGrid(i, j) && grid[i][j] == currentPlayer.getColor()) {
                    pions++;
                }
            }
        }
        return pions;
    }

    private boolean isInGrid(int x, int y) {
        //Methode de type booleen qui retourne vrai
        // si les coordonnees du pions qu'on lui a fourni correspondent à une case de la grille
        //Prendre en compte les case du milieu?
        return ((x >= MIN_LENGTH_GRID) && (x < MAX_LENGTH_GRID) && (y >= MIN_LENGTH_GRID) && (y < MAX_LENGTH_GRID) && !(x >= MIN_PROHIBITED && x < MAX_PROHIBITED && y >= MIN_PROHIBITED && y < MAX_PROHIBITED));
    }

    private boolean isMovePossibleForPlayer(Player p) {
        //Methode renvoyant vrai si l'un des joueur peut jouer et faux si aucun ne peut
        ArrayList<Position> lp = getListPosition(p);//On obtient les listes des pions des deux joueurs
        for (Position pi : lp) {//On parcours la liste des pions
            if (isEmptyArround(pi)) {//Si l'un des pions a une possibilité de jouer, la methode retourne vraie
                return true;
            }
        }
        return false;//Sinon elle retourne faux
    }

    private ArrayList getListPosition(Player p) {//Methode retournant la liste des positions des pions d'un joueur donne
        ArrayList<Position> lp = new ArrayList<>();
        for (int i = MIN_LENGTH_GRID; i < MAX_LENGTH_GRID; i++) {
            for (int j = MIN_LENGTH_GRID; j < MAX_LENGTH_GRID; j++) {
                if (grid[i][j] == p.getColor()) {
                    //Si la case de la grille etudiee est de la couleur du joueur en parametre de la methode,
                    // on ajoute le position à la liste
                    lp.add(new Position(i, j));
                }
            }
        }
        return lp;
    }

    private boolean isEmptyArround(Position p) {//Methode renvoyant vraie si le pion etudie peut jouer et faux sinon
        int x = p.getX();
        int y = p.getY();
        for (int i = x - JUMP_LENGTH; i <= x + JUMP_LENGTH; i++) {//On scan à une distance de deux autour du pion
            for (int j = y - JUMP_LENGTH; j <= y + JUMP_LENGTH; j++) {
                if ((isInGrid(i, j) == true) && (grid[i][j] == EMPTY)) {

                    //Si il y a une case vide et donc jouable on renvoie vrai
                    return true;
                }
            }
        }
        return false;
    }

    private int distanceCalculator(Position a, Position b) {
        //Methode renvoyant la distance entre deux positions arrondie à l'unite
        int xA = a.getX();
        int yA = a.getY();
        int xB = b.getX();
        int yB = b.getY();
        int lambdaX = (xB - xA);
        int lambdaY = (yB - yA);
        int result = (int) Math.floor(Math.sqrt(((lambdaX * lambdaX) + (lambdaY * lambdaY))));
        //Renvoi l'arrondi de la norme du vecteur ab
        return result;
    }

    public void interruptGame() {
        //Méthode qui permet à ce jour uniquement de sauvegarder et non d'interrompre une partie.
        int numberOfSave = 1;
        final String PSEUDO_P1 = p1.getPseudo();
        final String PSEUDO_P2 = p2.getPseudo();
        String nameOfSave = "src/pSaves/save_" + PSEUDO_P1 + "_" + PSEUDO_P2 + "_";
        File f = new File(nameOfSave + numberOfSave + ".svc");
        //On souhaite vérifier si le fichier existe
        while (f.exists()) {
            f = new File(nameOfSave + ++numberOfSave+".svc");
        }


        //On écrit dans le fichier les atribut du jouer p1,p2,current player, puis on écris le tableau
        try (FileWriter file = new FileWriter(f)) {
            file.write(p1.getPseudo() + " " + p1.getColor() + " " + p1.isAI() + System.lineSeparator());
            file.write(p2.getPseudo() + " " + p2.getColor() + " " + p2.isAI() + System.lineSeparator());
            file.write(currentPlayer.getColor() + System.lineSeparator());
            file.write(Difficulty + System.lineSeparator());
            for (int i = MIN_LENGTH_GRID; i < MAX_LENGTH_GRID; i++) {
                for (int j = MIN_LENGTH_GRID; j < MAX_LENGTH_GRID; j++) {
                    String infos = Integer.toString(grid[i][j]);
                    file.write(infos + System.lineSeparator());
                }
                // file.write(System.lineSeparator());
            }
           
            file.close();

        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        f.setReadOnly();
        
    }

    public void resumeGame(File f) throws FileNotFoundException, IOException {

        try (
                FileReader entree = new FileReader(f);
                BufferedReader br = new BufferedReader(entree)) {

            String separator = " ";

            //On récupère les 3 première lignes : Joueur 1, Joueur 2, Joueur currentPlayer
            String sp1 = br.readLine();
            String sp2 = br.readLine();           
            String sCurrentPlayer = br.readLine();
            String sp3 = br.readLine();
            // On stocke les infos des joueur dans des tableau de srting
            String infos[] = sp1.split(separator);
            this.p1 = new Player(infos[0], Integer.parseInt(infos[1]), Boolean.parseBoolean(infos[2]));
            //On créé le joueur

            infos = sp2.split(separator);
            //Idem
            this.p2 = new Player(infos[0], Integer.parseInt(infos[1]), Boolean.parseBoolean(infos[2]));
            int color = Integer.parseInt(sCurrentPlayer.substring(0));
            //On crée current player
            if (color == p1.getColor()) {
                this.currentPlayer = p1;
            } else {
                this.currentPlayer = p2;
            }
            infos = sp3.split(separator);
            setDifficulty(Boolean.parseBoolean(infos[0]));
            int valuesOfGrid[] = new int[MAX_LENGTH_GRID * MAX_LENGTH_GRID];
            //On souhaite initialiser le tableau au valeur inscrite dans le fichier

            for (int i = MIN_LENGTH_GRID; i < MAX_LENGTH_GRID * MAX_LENGTH_GRID; i++) {
                try {
                    valuesOfGrid[i] = Integer.parseInt(br.readLine()); // ON lis le fichier
                } catch (java.lang.NumberFormatException e) {
                }
            }

            int k = 0;
            this.grid = new int[MAX_LENGTH_GRID][MAX_LENGTH_GRID];
            for (int i = MIN_LENGTH_GRID; i < MAX_LENGTH_GRID; i++) {
                for (int j = MIN_LENGTH_GRID; j < MAX_LENGTH_GRID; j++) {
                    this.grid[i][j] = valuesOfGrid[k++]; //On crée le plateau
                }
            }

        }

    }

    public Player pickTheWinner() {
        //Cette methode designe le gagnant en comptant les pions des deux joueurs une fois la partie arrete
        int nb1 = countPawns(p1);//On appelle la methode countPawns pour compter les pions de chaque joueur
        int nb2 = countPawns(p2);
        if (nb1 > nb2) {
            return p1;
        } else if (nb2 > nb1) {
            return p2;
        } else {
            return new Player("nobody", 3, false);//En cas d'egalite, le jeu renvoie "nobody"
        }
    }

    private Player pickTheFirstPlayer() {//Methode renvoyant le joueur qui commence de maniere aleatoire

        int randomInt = 1 + (int) (Math.random() * 2);
        if (randomInt == 1) {
            return p1;

        } else {
            return p2;

        }
    }

    public int getColor(Position p) {
        int i = p.getX();
        int j = p.getY();
        return grid[i][j];
    }

    public String getPlayerColor() {
        int color = currentPlayer.getColor();
        switch (color) {
            case RED:
                return "red";
            case YELLOW:
                return "yellow";
            case BLUE:
                return "blue";
            case GREEN:
                return "green";
            default:
                return null;
        }

    }
    

}
