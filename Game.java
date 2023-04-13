//Mert Can Gonen
//181101039

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Game extends AbstractGame {

    private int turn; // if = 0, red turn; else if = 1, white turn.
    private boolean redSahKontrol = false; // red'e sah cekilip cekilmedigini kontrol eder.
    private boolean whiteSahKontrol = false; // white'a sah cekilip cekilmedigini kontrol eder.

    public Game(String playerA, String playerB) {
        Player redPlayer = new Player(playerA);
        redPlayer.puan = 0;
        Player whitePlayer = new Player(playerB);
        whitePlayer.puan = 0;
        Board board = new Board();
        this.turn = 0;
        this.setRed(redPlayer);
        this.setWhite(whitePlayer);
        setBoard(board);
    }

    // "SAH MAT! X oyunu kazandi. X'in puani: A, Y'nin puani:B"
    void play(String from, String to) {
        boolean positionsIsCorrect = true;
        if (from.length() != 2 || to.length() != 2) {
            positionsIsCorrect = false;
            System.out.println("hatali hareket");
        } else {
            char v1 = from.charAt(0);
            char h1 = from.charAt(1);
            char v2 = to.charAt(0);
            char h2 = to.charAt(1);
            if (v1 > 'j' || v2 > 'j' || v1 < 'a' || v2 < 'a' || h1 > '9' || h2 > '9' || h1 < '1' || h2 < '1') {
                positionsIsCorrect = false;
                System.out.println("hatali hareket");
            }
        }
        if (positionsIsCorrect) {
            int[] oldPosition = this.findPositionOnItemPositionsArray(from);
            int[] newPosition = this.findPositionOnItemPositionsArray(to);
            int v1 = oldPosition[0];
            int h1 = oldPosition[1];
            String item = this.getBoard().getItemPositions()[v1][h1];
            switch (item) {
                case "K":
                    playWhiteKaleItem(from, to, oldPosition, newPosition);
                    break;
                case "k":
                    playRedKaleItem(from, to, oldPosition, newPosition);
                    break;
                case "A":
                    playWhiteAtItem(from, to, oldPosition, newPosition);
                    break;
                case "a":
                    playRedAtItem(from, to, oldPosition, newPosition);
                    break;
                case "F":
                    playWhiteFilItem(from, to, oldPosition, newPosition);
                    break;
                case "f":
                    playRedFilItem(from, to, oldPosition, newPosition);
                    break;
                case "V":
                    playWhiteVezirItem(from, to, oldPosition, newPosition);
                    break;
                case "v":
                    playRedVezirItem(from, to, oldPosition, newPosition);
                    break;
                case "Ş":
                    playWhiteSahItem(from, to, oldPosition, newPosition);
                    break;
                case "ş":
                    playRedSahItem(from, to, oldPosition, newPosition);
                    break;
                case "T":
                    playWhiteTopItem(from, to, oldPosition, newPosition);
                    break;
                case "t":
                    playRedTopItem(from, to, oldPosition, newPosition);
                    break;
                case "P":
                    playWhitePiyonItem(from, to, oldPosition, newPosition);
                    break;
                case "p":
                    playRedPiyonItem(from, to, oldPosition, newPosition);
                    break;
                default:
                    System.out.println("hatali hareket");
                    break;
            }
            String[][] tmp = this.createTmpArray(this.getBoard().getItemPositions());
            boolean isSahFound = false;
            int v = 0;
            int h = 0;
            /*
             * if (this.turn == 0 && this.redSahKontrol) {
             * for (int i = 0; i < 10; i++) {
             * for (int j = 0; j < 9; j++) {
             * if (tmp[i][j].equals("ş")) {
             * v = i;
             * h = j;
             * isSahFound = true;
             * }
             * if (isSahFound)
             * break;
             * }
             * }
             * if (v >= 7) { // ustune bak
             * 
             * }
             * } else if (this.turn == 1 && this.whiteSahKontrol) {
             * 
             * }
             */
        }
    }

    public String[][] createTmpArray(String[][] itemPositions) {
        String[][] tmp = new String[10][9];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                tmp[i][j] = itemPositions[i][j];
            }
        }
        return tmp;
    }

    // Done
    public void playRedKaleItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean oldSah = this.redSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        ArrayList<String> redItems = new ArrayList<>(Arrays.asList("k", "a", "f", "v", "ş", "t", "p"));
        ArrayList<String> whiteItems = new ArrayList<>(Arrays.asList("K", "A", "F", "V", "Ş", "T", "P", "-"));
        ArrayList<String> routeOfKale = new ArrayList<>();
        boolean isRouteDirect = true;
        if (v1 != v2 && h1 == h2) { // vertical route on board
            if (v1 < v2) { // from up to bottom
                for (int i = v1 + 1; i < v2 + 1; i++) {
                    routeOfKale.add(this.getBoard().getItemPositions()[i][h1]);
                }
            } else { // from bottom to up
                for (int i = v1 - 1; i > v2 - 1; i--) {
                    routeOfKale.add(this.getBoard().getItemPositions()[i][h1]);
                }
            }
        } else if (v1 == v2 && h1 != h2) { // horizontal route on board
            if (h1 < h2) { // from left to right
                for (int i = h1 + 1; i < h2 + 1; i++) {
                    routeOfKale.add(this.getBoard().getItemPositions()[v1][i]);
                }
            } else { // from right to left
                for (int i = h1 - 1; i > h2 - 1; i--) {
                    routeOfKale.add(this.getBoard().getItemPositions()[v1][i]);
                }
            }
        } else {
            System.out.println("hatali hareket");
            isRouteDirect = false;
        }
        if (isRouteDirect) {
            boolean isItemOnRoute = false;
            for (int i = 0; i < routeOfKale.size() - 1; i++) { // son eleman rakip tas olabilir
                if (!routeOfKale.get(i).equals("-")) {
                    isItemOnRoute = true;
                    break;
                }
            }
            if (redItems.contains(routeOfKale.get(routeOfKale.size() - 1))) { // yol bos ama son item red
                isItemOnRoute = true;
            }
            if (!isItemOnRoute && this.turn == 0 && !from.equals(to)) {
                if (whiteItems.contains(routeOfKale.get(routeOfKale.size() - 1))) { // yol ya bos ya tas yutulacak
                    for (int i = 0; i < 32; i++) {
                        if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                            if (routeOfKale.get(routeOfKale.size() - 1).equals("-")) { // son item bos, tas yutmadi
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 1;
                                this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                                this.isWhiteOnSah(newPosition, "k");
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForRed(); // red'e sah cekilecek durum olustu mu
                                if (flyingGeneral || this.redSahKontrol) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.whiteSahKontrol = false;
                                    this.turn = 0;
                                }
                                break;
                            } else { // son item white, tas yuttu
                                int index = 0;
                                for (int j = 0; j < 32; j++) {
                                    if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                        index = j;
                                    }
                                }
                                this.getBoard().getItems()[index].setPosition("XX");
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 1;
                                this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                                this.isWhiteOnSah(newPosition, "k");
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForRed(); // red'e sah cekilecek durum olustu mu
                                if (flyingGeneral || this.redSahKontrol) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[index].setPosition("to");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.whiteSahKontrol = false;
                                    this.turn = 0;
                                }
                                break;
                            }
                        }
                    }
                }
            } else {
                if (this.turn == 1) {
                    System.out.println("Hamle sirasi " + this.getWhite().getName() + " isimli white oyuncuya ait!");
                } else {
                    System.out.println("hatali hareket");
                }
            }
        }
        if (oldSah && redSahKontrol) { // piazzadaki posta gore sah check altinda hatali hamlede bu cikti isteniyor
            System.out.println("hatali hareket. SAH MAT! " + this.white.name + " oyunu kazandi. " + this.white.name
                    + "'in puani: " + this.white.puan + ", " + this.red.name + "'nin puani: " + this.red.puan);
        }
    }

    // Done
    public void playWhiteKaleItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean oldSah = this.whiteSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        ArrayList<String> whiteItems = new ArrayList<>(Arrays.asList("K", "A", "F", "V", "Ş", "T", "P"));
        ArrayList<String> redItems = new ArrayList<>(Arrays.asList("k", "a", "f", "v", "ş", "t", "p", "-"));
        ArrayList<String> routeOfKale = new ArrayList<>();
        boolean isRouteDirect = true;
        if (v1 != v2 && h1 == h2) { // vertical route on board
            if (v1 < v2) { // from up to bottom
                for (int i = v1 + 1; i < v2 + 1; i++) {
                    routeOfKale.add(this.getBoard().getItemPositions()[i][h1]);
                }
            } else { // from bottom to up
                for (int i = v1 - 1; i > v2 - 1; i--) {
                    routeOfKale.add(this.getBoard().getItemPositions()[i][h1]);
                }
            }
        } else if (v1 == v2 && h1 != h2) { // horizontal route on board
            if (h1 < h2) { // from left to right
                for (int i = h1 + 1; i < h2 + 1; i++) {
                    routeOfKale.add(this.getBoard().getItemPositions()[v1][i]);
                }
            } else { // from right to left
                for (int i = h1 - 1; i > h2 - 1; i--) {
                    routeOfKale.add(this.getBoard().getItemPositions()[v1][i]);
                }
            }
        } else {
            System.out.println("hatali hareket");
            isRouteDirect = false;
        }
        if (isRouteDirect) {
            boolean isItemOnRoute = false;
            for (int i = 0; i < routeOfKale.size() - 1; i++) { // son eleman rakip tas olabilir
                if (!routeOfKale.get(i).equals("-")) {
                    isItemOnRoute = true;
                    break;
                }
            }
            if (whiteItems.contains(routeOfKale.get(routeOfKale.size() - 1))) { // yol bos ama son item white
                isItemOnRoute = true;
            }
            if (!isItemOnRoute && this.turn == 1 && !from.equals(to)) {
                if (redItems.contains(routeOfKale.get(routeOfKale.size() - 1))) { // yol ya bos ya tas yutulacak
                    for (int i = 0; i < 32; i++) {
                        if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                            if (routeOfKale.get(routeOfKale.size() - 1).equals("-")) { // son item bos, tas yutmadi
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 0;
                                this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                                this.isRedOnSah(newPosition, "K");
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForWhite(); // red'e sah cekilecek durum olustu mu
                                if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.redSahKontrol = false;
                                    this.turn = 1;
                                }
                                break;
                            } else { // son item white, tas yuttu
                                int index = 0;
                                for (int j = 0; j < 32; j++) {
                                    if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                        index = j;
                                    }
                                }
                                this.getBoard().getItems()[index].setPosition("XX");
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 0;
                                this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                                this.isRedOnSah(newPosition, "K");
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForWhite(); // red'e sah cekilecek durum olustu mu
                                if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[index].setPosition("to");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.redSahKontrol = false;
                                    this.turn = 1;
                                }
                                break;
                            }
                        }
                    }
                }
            } else {
                if (this.turn == 0) {
                    System.out.println("Hamle sirasi " + this.getRed().getName() + " isimli red oyuncuya ait!");
                } else {
                    System.out.println("hatali hareket");
                }
            }
        }
        if (oldSah && whiteSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.red.name + " oyunu kazandi. " + this.red.name
                    + "'in puani: " + this.red.puan + ", " + this.white.name + "'nin puani: " + this.white.puan);
        }
    }

    // Done
    public void playRedAtItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean isRouteEmpty = true;
        boolean oldSah = this.redSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        String itemOnRoad = "";
        ArrayList<String> redItems = new ArrayList<>(Arrays.asList("k", "a", "f", "v", "ş", "t", "p"));
        if (v1 - v2 == 2 && Math.abs(h1 - h2) == 1) { // ust tarafa oynuyor
            itemOnRoad = this.getBoard().getItemPositions()[v1 - 1][h1];
        } else if (h1 - h2 == 2 && Math.abs(v1 - v2) == 1) { // sol tarafa oynuyor
            itemOnRoad = this.getBoard().getItemPositions()[v1][h1 - 1];
        } else if (v2 - v1 == 2 && Math.abs(h1 - h2) == 1) { // alt tarafa oynuyor
            itemOnRoad = this.getBoard().getItemPositions()[v1 + 1][h1];
        } else if (h2 - h1 == 2 && Math.abs(v1 - v2) == 1) { // sag tarafa oynuyor
            itemOnRoad = this.getBoard().getItemPositions()[v1][h1 + 2];
        }
        if (!itemOnRoad.equals("-")) {
            isRouteEmpty = false;
        }
        if (isRouteEmpty && this.turn == 0 && !from.equals(to) && !redItems.contains(itemOnNewPosition)) {
            if ((Math.abs(v1 - v2) == 2 && Math.abs(h1 - h2) == 1)
                    || (Math.abs(v1 - v2) == 1 && Math.abs(h1 - h2) == 2)) {
                for (int i = 0; i < 32; i++) {
                    if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                        if (itemOnNewPosition.equals("-")) { // bos alana oynadi
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 1;
                            this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                            this.isWhiteOnSah(newPosition, "a");
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForRed(); // red'e sah cekilecek durum olustu mu
                            if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.whiteSahKontrol = false;
                                this.turn = 0;
                            }
                            break;
                        } else { // tas olan alana oynadi
                            int index = 0;
                            for (int j = 0; j < 32; j++) {
                                if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                    index = j;
                                }
                            }
                            this.getBoard().getItems()[index].setPosition("XX");
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 1;
                            this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                            this.isWhiteOnSah(newPosition, "a");
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForRed(); // red'e sah cekilecek durum olustu mu
                            if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[index].setPosition("to");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.whiteSahKontrol = false;
                                this.turn = 0;
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            if (this.turn == 1) {
                System.out.println("Hamle sirasi " + this.getWhite().getName() + " isimli white oyuncuya ait!");
            } else {
                System.out.println("hatali hareket");
            }
        }
        if (oldSah && redSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.white.name + " oyunu kazandi. " + this.white.name
                    + "'in puani: " + this.white.puan + ", " + this.red.name + "'nin puani: " + this.red.puan);

        }
    }

    // Done
    public void playWhiteAtItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean isRouteEmpty = true;
        boolean oldSah = this.whiteSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        String itemOnRoad = "";
        ArrayList<String> whiteItems = new ArrayList<>(Arrays.asList("K", "A", "F", "V", "Ş", "T", "P"));
        if (v1 - v2 == 2 && Math.abs(h1 - h2) == 1) { // ust tarafa oynuyor
            itemOnRoad = this.getBoard().getItemPositions()[v1 - 1][h1];
        } else if (h1 - h2 == 2 && Math.abs(v1 - v2) == 1) { // sol tarafa oynuyor
            itemOnRoad = this.getBoard().getItemPositions()[v1][h1 - 1];
        } else if (v2 - v1 == 2 && Math.abs(h1 - h2) == 1) { // alt tarafa oynuyor
            itemOnRoad = this.getBoard().getItemPositions()[v1 + 1][h1];
        } else if (h2 - h1 == 2 && Math.abs(v1 - v2) == 1) { // sag tarafa oynuyor
            itemOnRoad = this.getBoard().getItemPositions()[v1][h1 + 2];
        }
        if (!itemOnRoad.equals("-")) {
            isRouteEmpty = false;
        }
        if (isRouteEmpty && this.turn == 1 && !from.equals(to) && !whiteItems.contains(itemOnNewPosition)) {
            if ((Math.abs(v1 - v2) == 2 && Math.abs(h1 - h2) == 1)
                    || (Math.abs(v1 - v2) == 1 && Math.abs(h1 - h2) == 2)) {
                for (int i = 0; i < 32; i++) {
                    if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                        if (itemOnNewPosition.equals("-")) { // bos alana oynadi
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 0;
                            this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                            this.isRedOnSah(newPosition, "A");
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForWhite(); // white'a sah cekilecek durum olustu mu
                            if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.redSahKontrol = false;
                                this.turn = 1;
                            }
                            break;
                        } else { // tas olan alana oynadi
                            int index = 0;
                            for (int j = 0; j < 32; j++) {
                                if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                    index = j;
                                }
                            }
                            this.getBoard().getItems()[index].setPosition("XX");
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 0;
                            this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                            this.isRedOnSah(newPosition, "A");
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForWhite(); // white'a sah cekilecek durum olustu mu
                            if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[index].setPosition("to");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.redSahKontrol = false;
                                this.turn = 1;
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            if (this.turn == 0) {
                System.out.println("Hamle sirasi " + this.getRed().getName() + " isimli red oyuncuya ait!");
            } else {
                System.out.println("hatali hareket");
            }
        }
        if (oldSah && whiteSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.red.name + " oyunu kazandi. " + this.red.name
                    + "'in puani: " + this.red.puan + ", " + this.white.name + "'nin puani: " + this.white.puan);

        }
    }

    // Done
    public void playRedFilItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean isNextEmpty = true; // taslarin uzerinden atlayamadigi icin 2 noktayi da kontrol ederiz
        boolean oldSah = this.redSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        ArrayList<String> whiteItems = new ArrayList<>(Arrays.asList("K", "A", "F", "V", "Ş", "T", "P", "-"));
        ArrayList<String> possibleRedFilPositions = new ArrayList<>(
                Arrays.asList("a3", "c5", "e3", "c1", "a7", "e7", "c9"));
        int tmpV = 0;
        int tmpH = 0;
        if (from.equals("a3")) {
            if (to.equals("c5")) { // b4
                tmpV = v1 - 1;
                tmpH = h1 + 1;
            } else if (to.equals("c1")) { // b2
                tmpV = v1 - 1;
                tmpH = h1 - 1;
            }
        } else if (from.equals("c5")) {
            if (to.equals("a3")) { // b4
                tmpV = v1 + 1;
                tmpH = h1 - 1;
            } else if (to.equals("a7")) { // b6
                tmpV = v1 + 1;
                tmpH = h1 + 1;
            } else if (to.equals("e3")) { // d4
                tmpV = v1 - 1;
                tmpH = h1 - 1;
            } else if (to.equals("e7")) { // d6
                tmpV = v1 - 1;
                tmpH = h1 + 1;
            }
        } else if (from.equals("e3")) {
            if (to.equals("c1")) { // d2
                tmpV = v1 + 1;
                tmpH = h1 - 1;
            } else if (to.equals("c5")) { // d4
                tmpV = v1 - 1;
                tmpH = h1 + 1;
            }
        } else if (from.equals("c1")) {
            if (to.equals("e3")) { // d2
                tmpV = v1 - 1;
                tmpH = h1 + 1;
            } else if (to.equals("a3")) { // b2
                tmpV = v1 + 1;
                tmpH = h1 + 1;
            }
        } else if (from.equals("a7")) {
            if (to.equals("c9")) { // b8
                tmpV = v1 - 1;
                tmpH = h1 + 1;
            } else if (to.equals("c5")) { // b6
                tmpV = v1 - 1;
                tmpH = h1 - 1;
            }
        } else if (from.equals("e7")) {
            if (to.equals("c5")) { // d6
                tmpV = v1 + 1;
                tmpH = h1 - 1;
            } else if (to.equals("c9")) { // d8
                tmpV = v1 + 1;
                tmpH = h1 + 1;
            }
        } else if (from.equals("c9")) {
            if (to.equals("a7")) { // b8
                tmpV = v1 + 1;
                tmpH = h1 - 1;
            } else if (to.equals("e7")) { // d8
                tmpV = v1 - 1;
                tmpH = h1 - 1;
            }
        }
        if (!this.getBoard().getItemPositions()[tmpV][tmpH].equals("-")) {
            isNextEmpty = false;
        }
        if (possibleRedFilPositions.contains(to) && this.turn == 0 && !from.equals(to) && isNextEmpty == true) {
            if ((Math.abs(v1 - v2) == 2 && Math.abs(h1 - h2) == 2) && (whiteItems.contains(itemOnNewPosition))) {
                for (int i = 0; i < 32; i++) {
                    if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                        if (itemOnNewPosition.equals("-")) { // bos alana oynadi
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 1;
                            this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForRed();
                            if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.turn = 0;
                            }
                            break;
                        } else { // tas olan alana oynadi
                            int index = 0;
                            for (int j = 0; j < 32; j++) {
                                if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                    index = j;
                                }
                            }
                            this.getBoard().getItems()[index].setPosition("XX");
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 1;
                            this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForRed();
                            if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[index].setPosition("to");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.turn = 0;
                            }
                            break;
                        }
                    }
                }
            } else {
                System.out.println("hatali hareket");
            }
        } else {
            if (this.turn == 1) {
                System.out.println("Hamle sirasi " + this.getWhite().getName() + " isimli white oyuncuya ait!");
            } else {
                System.out.println("hatali hareket");
            }
        }
        if (oldSah && redSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.white.name + " oyunu kazandi. " + this.white.name
                    + "'in puani: " + this.white.puan + ", " + this.red.name + "'nin puani: " + this.red.puan);

        }
    }

    // Done
    public void playWhiteFilItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean isNextEmpty = true; // taslarin uzerinden atlayamadigi icin 2 noktayi da kontrol ederiz
        boolean oldSah = this.whiteSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        ArrayList<String> redItems = new ArrayList<>(Arrays.asList("k", "a", "f", "v", "ş", "t", "p", "-"));
        ArrayList<String> possibleWhiteFilPositions = new ArrayList<>(
                Arrays.asList("j3", "h5", "f3", "h1", "j7", "f7", "h9"));
        int tmpV = 0;
        int tmpH = 0;
        if (from.equals("j3")) {
            if (to.equals("h5")) { // i4
                tmpV = v1 + 1;
                tmpH = h1 + 1;
            } else if (to.equals("h1")) { // i2
                tmpV = v1 + 1;
                tmpH = h1 - 1;
            }
        } else if (from.equals("h5")) {
            if (to.equals("j3")) { // i4
                tmpV = v1 - 1;
                tmpH = h1 - 1;
            } else if (to.equals("j7")) { // i6
                tmpV = v1 - 1;
                tmpH = h1 + 1;
            } else if (to.equals("f3")) { // g4
                tmpV = v1 + 1;
                tmpH = h1 - 1;
            } else if (to.equals("f7")) { // g6
                tmpV = v1 + 1;
                tmpH = h1 + 1;
            }
        } else if (from.equals("f3")) {
            if (to.equals("h1")) { // g2
                tmpV = v1 - 1;
                tmpH = h1 - 1;
            } else if (to.equals("h5")) { // g4
                tmpV = v1 - 1;
                tmpH = h1 + 1;
            }
        } else if (from.equals("h1")) {
            if (to.equals("j3")) { // i2
                tmpV = v1 - 1;
                tmpH = h1 + 1;
            } else if (to.equals("f3")) { // g2
                tmpV = v1 + 1;
                tmpH = h1 + 1;
            }
        } else if (from.equals("j7")) {
            if (to.equals("h9")) { // i8
                tmpV = v1 + 1;
                tmpH = h1 + 1;
            } else if (to.equals("h5")) { // i6
                tmpV = v1 + 1;
                tmpH = h1 - 1;
            }
        } else if (from.equals("f7")) {
            if (to.equals("h5")) { // g6
                tmpV = v1 - 1;
                tmpH = h1 - 1;
            } else if (to.equals("h9")) { // g8
                tmpV = v1 - 1;
                tmpH = h1 + 1;
            }
        } else if (from.equals("h9")) {
            if (to.equals("j7")) { // i8
                tmpV = v1 - 1;
                tmpH = h1 - 1;
            } else if (to.equals("f7")) { // g8
                tmpV = v1 + 1;
                tmpH = h1 - 1;
            }
        }
        if (!this.getBoard().getItemPositions()[tmpV][tmpH].equals("-")) {
            isNextEmpty = false;
        }
        if (possibleWhiteFilPositions.contains(to) && this.turn == 1 && !from.equals(to) && isNextEmpty == true) {
            if ((Math.abs(v1 - v2) == 2 && Math.abs(h1 - h2) == 2) && (redItems.contains(itemOnNewPosition))) {
                for (int i = 0; i < 32; i++) {
                    if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                        if (itemOnNewPosition.equals("-")) { // bos alana oynadi
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 0;
                            this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForWhite();
                            if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.turn = 1;
                            }
                            break;
                        } else { // tas olan alana oynadi
                            int index = 0;
                            for (int j = 0; j < 32; j++) {
                                if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                    index = j;
                                }
                            }
                            this.getBoard().getItems()[index].setPosition("XX");
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 0;
                            this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForWhite();
                            if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[index].setPosition("to");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.turn = 1;
                            }
                            break;
                        }
                    }
                }
            } else {
                System.out.println("hatali hareket");
            }
        } else {
            if (this.turn == 0) {
                System.out.println("Hamle sirasi " + this.getRed().getName() + " isimli red oyuncuya ait!");
            } else {
                System.out.println("hatali hareket");
            }
        }
        if (oldSah && whiteSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.red.name + " oyunu kazandi. " + this.red.name
                    + "'in puani: " + this.red.puan + ", " + this.white.name + "'nin puani: " + this.white.puan);

        }
    }

    // Done
    public void playRedVezirItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean oldSah = this.redSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        ArrayList<String> whiteItems = new ArrayList<>(Arrays.asList("K", "A", "F", "V", "Ş", "T", "P", "-"));
        ArrayList<String> possibleRedVezirPositions = new ArrayList<>(Arrays.asList("a4", "a6", "b5", "c4", "c6"));
        if (possibleRedVezirPositions.contains(to) && this.turn == 0 && !from.equals(to)) {
            if ((Math.abs(v1 - v2) == 1 && Math.abs(h1 - h2) == 1) && (whiteItems.contains(itemOnNewPosition))) {
                for (int i = 0; i < 32; i++) {
                    if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                        if (itemOnNewPosition.equals("-")) { // bos alana oynadi
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 1;
                            this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForRed();
                            if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.turn = 0;
                            }
                            break;
                        } else { // tas olan alana oynadi
                            int index = 0;
                            for (int j = 0; j < 32; j++) {
                                if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                    index = j;
                                }
                            }
                            this.getBoard().getItems()[index].setPosition("XX");
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 1;
                            this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForRed();
                            if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[index].setPosition("to");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.turn = 0;
                            }
                            break;
                        }
                    }
                }
            } else {
                System.out.println("hatali hareket");
            }
        } else {
            if (this.turn == 1) {
                System.out.println("Hamle sirasi " + this.getWhite().getName() + " isimli white oyuncuya ait!");
            } else {
                System.out.println("hatali hareket");
            }
        }
        if (oldSah && redSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.white.name + " oyunu kazandi. " + this.white.name
                    + "'in puani: " + this.white.puan + ", " + this.red.name + "'nin puani: " + this.red.puan);

        }
    }

    // Done
    public void playWhiteVezirItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean oldSah = this.whiteSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        ArrayList<String> redItems = new ArrayList<>(Arrays.asList("k", "a", "f", "v", "ş", "t", "p", "-"));
        ArrayList<String> possibleWhiteVezirPositions = new ArrayList<>(Arrays.asList("j4", "j6", "i5", "h4", "h6"));
        if (possibleWhiteVezirPositions.contains(to) && this.turn == 1 && !from.equals(to)) {
            if ((Math.abs(v1 - v2) == 1 && Math.abs(h1 - h2) == 1) && (redItems.contains(itemOnNewPosition))) {
                for (int i = 0; i < 32; i++) {
                    if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                        if (itemOnNewPosition.equals("-")) { // bos alana oynadi
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 0;
                            this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForWhite();
                            if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.turn = 1;
                            }
                            break;
                        } else { // tas olan alana oynadi
                            int index = 0;
                            for (int j = 0; j < 32; j++) {
                                if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                    index = j;
                                }
                            }
                            this.getBoard().getItems()[index].setPosition("XX");
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 0;
                            this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForWhite();
                            if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[index].setPosition("to");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.turn = 1;
                            }
                            break;
                        }
                    }
                }
            } else {
                System.out.println("hatali hareket");
            }
        } else {
            if (this.turn == 0) {
                System.out.println("Hamle sirasi " + this.getRed().getName() + " isimli red oyuncuya ait!");
            } else {
                System.out.println("hatali hareket");
            }
        }
        if (oldSah && whiteSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.red.name + " oyunu kazandi. " + this.red.name
                    + "'in puani: " + this.red.puan + ", " + this.white.name + "'nin puani: " + this.white.puan);

        }
    }

    // Done
    public void playRedSahItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean oldSah = this.redSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        ArrayList<String> whiteItems = new ArrayList<>(Arrays.asList("K", "A", "F", "V", "Ş", "T", "P", "-"));
        ArrayList<String> possibleRedSahPositions = new ArrayList<>(
                Arrays.asList("a4", "a5", "a6", "b4", "b5", "b6", "c4", "c5", "c6"));
        if (possibleRedSahPositions.contains(to) && this.turn == 0 && !from.equals(to)) {
            if ((v1 == v2 && Math.abs(h1 - h2) == 1 || h1 == h2 && Math.abs(v1 - v2) == 1)
                    && (whiteItems.contains(itemOnNewPosition))) {
                for (int i = 0; i < 32; i++) {
                    if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                        if (itemOnNewPosition.equals("-")) { // bos alana oynadi
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 1;
                            this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForRed();
                            if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.turn = 0;
                            }
                            break;
                        } else { // tas olan alana oynadi
                            int index = 0;
                            for (int j = 0; j < 32; j++) {
                                if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                    index = j;
                                }
                            }
                            this.getBoard().getItems()[index].setPosition("XX");
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 1;
                            this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                            flyingGeneral = this.checkFlyingGeneralRule();
                            this.checkYourMoveMadeSahForRed();
                            if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[index].setPosition("to");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.turn = 0;
                            }
                            break;
                        }
                    }
                }
            } else {
                System.out.println("hatali hareket");
            }
        } else {
            if (this.turn == 1) {
                System.out.println("Hamle sirasi " + this.getWhite().getName() + " isimli white oyuncuya ait!");
            } else {
                System.out.println("hatali hareket");
            }
        }
        if (oldSah && redSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.white.name + " oyunu kazandi. " + this.white.name
                    + "'in puani: " + this.white.puan + ", " + this.red.name + "'nin puani: " + this.red.puan);

        }
    }

    // Done
    public void playWhiteSahItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean oldSah = this.whiteSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        ArrayList<String> redItems = new ArrayList<>(Arrays.asList("k", "a", "f", "v", "ş", "t", "p", "-"));
        ArrayList<String> possibleWhiteSahPositions = new ArrayList<>(
                Arrays.asList("j4", "j5", "j6", "i4", "i5", "i6", "h4", "h5", "h6"));
        if (possibleWhiteSahPositions.contains(to) && this.turn == 1 && !from.equals(to)) {
            if ((v1 == v2 && Math.abs(h1 - h2) == 1 || h1 == h2 && Math.abs(v1 - v2) == 1)
                    && (redItems.contains(itemOnNewPosition))) {
                for (int i = 0; i < 32; i++) {
                    if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                        if (itemOnNewPosition.equals("-")) { // bos alana oynadi
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 0;
                            flyingGeneral = this.checkFlyingGeneralRule();
                            if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 1;
                            }
                            break;
                        } else { // tas olan alana oynadi
                            int index = 0;
                            for (int j = 0; j < 32; j++) {
                                if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                    index = j;
                                }
                            }
                            this.getBoard().getItems()[index].setPosition("XX");
                            this.getBoard().getItems()[i].setPosition(to);
                            this.getBoard().setItemPositionsArrayPerItem();
                            this.turn = 0;
                            this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                            flyingGeneral = this.checkFlyingGeneralRule();
                            if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                System.out.println("hatali hareket");
                                this.getBoard().getItems()[index].setPosition("to");
                                this.getBoard().getItems()[i].setPosition(from);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                this.turn = 1;
                            }
                            break;
                        }
                    }
                }
            } else {
                System.out.println("hatali hareket");
            }
        } else {
            if (this.turn == 0) {
                System.out.println("Hamle sirasi " + this.getRed().getName() + " isimli red oyuncuya ait!");
            } else {
                System.out.println("hatali hareket");
            }
        }
        if (oldSah && whiteSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.red.name + " oyunu kazandi. " + this.red.name
                    + "'in puani: " + this.red.puan + ", " + this.white.name + "'nin puani: " + this.white.puan);

        }
    }

    // Done
    public void playRedTopItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean oldSah = this.redSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        ArrayList<String> redItems = new ArrayList<>(Arrays.asList("k", "a", "f", "v", "ş", "t", "p"));
        ArrayList<String> whiteItems = new ArrayList<>(Arrays.asList("K", "A", "F", "V", "Ş", "T", "P", "-"));
        ArrayList<String> routeOfTop = new ArrayList<>();
        boolean isRouteDirect = true; // yol duz
        if (v1 != v2 && h1 == h2) { // vertical route on board
            if (v1 < v2) { // from up to bottom
                for (int i = v1 + 1; i < v2 + 1; i++) {
                    routeOfTop.add(this.getBoard().getItemPositions()[i][h1]);
                }
            } else { // from bottom to up
                for (int i = v1 - 1; i > v2 - 1; i--) {
                    routeOfTop.add(this.getBoard().getItemPositions()[i][h1]);
                }
            }
        } else if (v1 == v2 && h1 != h2) { // horizontal route on board
            if (h1 < h2) { // from left to right
                for (int i = h1 + 1; i < h2 + 1; i++) {
                    routeOfTop.add(this.getBoard().getItemPositions()[v1][i]);
                }
            } else { // from right to left
                for (int i = h1 - 1; i > h2 - 1; i--) {
                    routeOfTop.add(this.getBoard().getItemPositions()[v1][i]);
                }
            }
        } else {
            System.out.println("hatali hareket");
            isRouteDirect = false;
        }
        boolean isRouteFree = true; // yol bos
        for (int i = 0; i < routeOfTop.size(); i++) {
            if (!routeOfTop.get(i).equals("-")) {
                isRouteFree = false;
                break;
            }
        }
        if (isRouteDirect) {
            boolean isLastItemRed = false;
            if (redItems.contains(itemOnNewPosition)) {
                isLastItemRed = true;
            }
            if (!isLastItemRed && this.turn == 0 && !from.equals(to)) {
                if (whiteItems.contains(routeOfTop.get(routeOfTop.size() - 1))) { // yol ya bos ya tas yutulacak
                    for (int i = 0; i < 32; i++) {
                        if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                            if (routeOfTop.get(routeOfTop.size() - 1).equals("-") && isRouteFree) { // son item, yol bos
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 1;
                                this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                                this.isWhiteOnSah(newPosition, "t");
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForRed();
                                if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.whiteSahKontrol = false;
                                    this.turn = 0;
                                }
                                break;
                            } else if (!isRouteFree) { // son item white, tas yuttu
                                int itemCount = 0;
                                for (int k = 0; k < routeOfTop.size(); k++) {
                                    if (!routeOfTop.get(k).equals("-")) {
                                        itemCount++;
                                    }
                                }
                                if (itemCount == 2) {
                                    int index = 0;
                                    for (int j = 0; j < 32; j++) {
                                        if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                            index = j;
                                        }
                                    }
                                    this.getBoard().getItems()[index].setPosition("XX");
                                    this.getBoard().getItems()[i].setPosition(to);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.turn = 1;
                                    this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                                    this.isWhiteOnSah(newPosition, "t");
                                    flyingGeneral = this.checkFlyingGeneralRule();
                                    this.checkYourMoveMadeSahForRed();
                                    if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                        System.out.println("hatali hareket");
                                        this.getBoard().getItems()[index].setPosition("to");
                                        this.getBoard().getItems()[i].setPosition(from);
                                        this.getBoard().setItemPositionsArrayPerItem();
                                        this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                        this.whiteSahKontrol = false;
                                        this.turn = 0;
                                    }
                                    break;
                                } else {
                                    System.out.println("hatali hareket");
                                }

                            }
                        }
                    }
                }
            } else {
                if (this.turn == 1) {
                    System.out.println("Hamle sirasi " + this.getWhite().getName() + " isimli white oyuncuya ait!");
                } else {
                    System.out.println("hatali hareket");
                }
            }
        }
        if (oldSah && redSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.white.name + " oyunu kazandi. " + this.white.name
                    + "'in puani: " + this.white.puan + ", " + this.red.name + "'nin puani: " + this.red.puan);

        }
    }

    // Done
    public void playWhiteTopItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean oldSah = this.whiteSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        ArrayList<String> redItems = new ArrayList<>(Arrays.asList("k", "a", "f", "v", "ş", "t", "p", "-"));
        ArrayList<String> whiteItems = new ArrayList<>(Arrays.asList("K", "A", "F", "V", "Ş", "T", "P"));
        ArrayList<String> routeOfTop = new ArrayList<>();
        boolean isRouteDirect = true; // yol duz
        if (v1 != v2 && h1 == h2) { // vertical route on board
            if (v1 < v2) { // from up to bottom
                for (int i = v1 + 1; i < v2 + 1; i++) {
                    routeOfTop.add(this.getBoard().getItemPositions()[i][h1]);
                }
            } else { // from bottom to up
                for (int i = v1 - 1; i > v2 - 1; i--) {
                    routeOfTop.add(this.getBoard().getItemPositions()[i][h1]);
                }
            }
        } else if (v1 == v2 && h1 != h2) { // horizontal route on board
            if (h1 < h2) { // from left to right
                for (int i = h1 + 1; i < h2 + 1; i++) {
                    routeOfTop.add(this.getBoard().getItemPositions()[v1][i]);
                }
            } else { // from right to left
                for (int i = h1 - 1; i > h2 - 1; i--) {
                    routeOfTop.add(this.getBoard().getItemPositions()[v1][i]);
                }
            }
        } else {
            System.out.println("hatali hareket");
            isRouteDirect = false;
        }
        boolean isRouteFree = true; // yol bos
        for (int i = 0; i < routeOfTop.size(); i++) {
            if (!routeOfTop.get(i).equals("-")) {
                isRouteFree = false;
                break;
            }
        }
        if (isRouteDirect) {
            boolean isLastItemWhite = false;
            if (whiteItems.contains(itemOnNewPosition)) {
                isLastItemWhite = true;
            }
            if (!isLastItemWhite && this.turn == 1 && !from.equals(to)) {
                if (redItems.contains(routeOfTop.get(routeOfTop.size() - 1))) { // yol ya bos ya tas yutulacak
                    for (int i = 0; i < 32; i++) {
                        if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                            if (routeOfTop.get(routeOfTop.size() - 1).equals("-") && isRouteFree) { // son item, yol bos
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 0;
                                this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                                this.isRedOnSah(newPosition, "T");
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForWhite();
                                if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.redSahKontrol = false;
                                    this.turn = 1;
                                }
                                break;
                            } else if (!isRouteFree) { // son item white, tas yuttu
                                int itemCount = 0;
                                for (int k = 0; k < routeOfTop.size(); k++) {
                                    if (!routeOfTop.get(k).equals("-")) {
                                        itemCount++;
                                    }
                                }
                                if (itemCount == 2) {
                                    int index = 0;
                                    for (int j = 0; j < 32; j++) {
                                        if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                            index = j;
                                        }
                                    }
                                    this.getBoard().getItems()[index].setPosition("XX");
                                    this.getBoard().getItems()[i].setPosition(to);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.turn = 0;
                                    this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                                    this.isRedOnSah(newPosition, "T");
                                    flyingGeneral = this.checkFlyingGeneralRule();
                                    this.checkYourMoveMadeSahForWhite();
                                    if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                        System.out.println("hatali hareket");
                                        this.getBoard().getItems()[index].setPosition("to");
                                        this.getBoard().getItems()[i].setPosition(from);
                                        this.getBoard().setItemPositionsArrayPerItem();
                                        this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                        this.redSahKontrol = false;
                                        this.turn = 1;
                                    }
                                    break;
                                } else {
                                    System.out.println("hatali hareket");
                                }

                            }
                        }
                    }
                }
            } else {
                if (this.turn == 0) {
                    System.out.println("Hamle sirasi " + this.getRed().getName() + " isimli red oyuncuya ait!");
                } else {
                    System.out.println("hatali hareket");
                }
            }
        }
        if (oldSah && whiteSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.red.name + " oyunu kazandi. " + this.red.name
                    + "'in puani: " + this.red.puan + ", " + this.white.name + "'nin puani: " + this.white.puan);

        }
    }

    // Done
    public void playRedPiyonItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean oldSah = this.redSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        ArrayList<String> redItems = new ArrayList<>(Arrays.asList("k", "a", "f", "v", "ş", "t", "p"));
        if (this.turn == 0 && !from.equals(to) && !redItems.contains(itemOnNewPosition)) {
            if (v1 <= 4) {// nehrin karsisinda
                if ((v1 == v2 && Math.abs(h2 - h1) == 1) || ((v1 - v2) == 1 && h1 == h2)) { // vertical and horizontal
                    for (int i = 0; i < 32; i++) {
                        if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                            if (itemOnNewPosition.equals("-")) { // bos alana oynadi
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 1;
                                this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                                this.isWhiteOnSah(newPosition, "p");
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForRed();
                                if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.whiteSahKontrol = false;
                                    this.turn = 0;
                                }
                                break;
                            } else { // tas olan alana oynadi
                                int index = 0;
                                for (int j = 0; j < 32; j++) {
                                    if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                        index = j;
                                    }
                                }
                                this.getBoard().getItems()[index].setPosition("XX");
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 1;
                                this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                                this.isWhiteOnSah(newPosition, "p");
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForRed();
                                if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[index].setPosition("to");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.whiteSahKontrol = false;
                                    this.turn = 0;
                                }
                                break;
                            }
                        }
                    }
                }
            } else { // kendi tarafinda
                if (v1 - v2 == 1 && h1 == h2) {
                    for (int i = 0; i < 32; i++) {
                        if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                            if (itemOnNewPosition.equals("-")) { // bos alana oynadi
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 1;
                                this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForRed();
                                if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.turn = 0;
                                }
                                break;
                            } else { // tas olan alana oynadi
                                int index = 0;
                                for (int j = 0; j < 32; j++) {
                                    if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                        index = j;
                                    }
                                }
                                this.getBoard().getItems()[index].setPosition("XX");
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 1;
                                this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForRed();
                                if ((flyingGeneral || redSahKontrol) && !oldSah) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[index].setPosition("to");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.turn = 0;
                                }
                                break;
                            }
                        }
                    }
                } else {
                    System.out.println("hatali hareket");
                }
            }
        } else {
            if (this.turn == 1) {
                System.out.println("Hamle sirasi " + this.getWhite().getName() + " isimli white oyuncuya ait!");
            } else {
                System.out.println("hatali hareket");
            }
        }
        if (oldSah && redSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.white.name + " oyunu kazandi. " + this.white.name
                    + "'in puani: " + this.white.puan + ", " + this.red.name + "'nin puani: " + this.red.puan);

        }
    }

    // Done
    public void playWhitePiyonItem(String from, String to, int[] oldPosition, int[] newPosition) {
        boolean flyingGeneral = false;
        boolean oldSah = this.whiteSahKontrol;
        int v1 = oldPosition[0];
        int h1 = oldPosition[1];
        int v2 = newPosition[0];
        int h2 = newPosition[1];
        String itemOnNewPosition = this.getBoard().getItemPositions()[v2][h2];
        ArrayList<String> whiteItems = new ArrayList<>(Arrays.asList("K", "A", "F", "V", "Ş", "T", "P"));
        if (this.turn == 1 && !from.equals(to) && !whiteItems.contains(itemOnNewPosition)) {
            if (v1 > 4) {// nehrin karsisinda
                if ((v1 == v2 && Math.abs(h2 - h1) == 1) || ((v2 - v1) == 1 && h1 == h2)) { // vertical and horizontal
                    for (int i = 0; i < 32; i++) {
                        if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                            if (itemOnNewPosition.equals("-")) { // bos alana oynadi
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 0;
                                this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                                this.isRedOnSah(newPosition, "P");
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForWhite();
                                if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.redSahKontrol = false;
                                    this.turn = 1;
                                }
                                break;
                            } else { // tas olan alana oynadi
                                int index = 0;
                                for (int j = 0; j < 32; j++) {
                                    if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                        index = j;
                                    }
                                }
                                this.getBoard().getItems()[index].setPosition("XX");
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 0;
                                this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                                this.isRedOnSah(newPosition, "P");
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForWhite();
                                if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[index].setPosition("to");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.redSahKontrol = false;
                                    this.turn = 1;
                                }
                                break;
                            }
                        }
                    }
                }
            } else { // kendi tarafinda
                if (v2 - v1 == 1 && h1 == h2) {
                    for (int i = 0; i < 32; i++) {
                        if (this.getBoard().getItems()[i].getPosition().equals(from)) {
                            if (itemOnNewPosition.equals("-")) { // bos alana oynadi
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 0;
                                this.red.setPuan(this.red.getPuan() + this.returnPoint(itemOnNewPosition));
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForWhite();
                                if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.red.setPuan(this.red.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.turn = 1;
                                }
                                break;
                            } else { // tas olan alana oynadi
                                int index = 0;
                                for (int j = 0; j < 32; j++) {
                                    if (this.getBoard().getItems()[j].getPosition().equals(to)) {
                                        index = j;
                                    }
                                }
                                this.getBoard().getItems()[index].setPosition("XX");
                                this.getBoard().getItems()[i].setPosition(to);
                                this.getBoard().setItemPositionsArrayPerItem();
                                this.turn = 0;
                                this.white.setPuan(this.white.getPuan() + this.returnPoint(itemOnNewPosition));
                                flyingGeneral = this.checkFlyingGeneralRule();
                                this.checkYourMoveMadeSahForWhite();
                                if ((flyingGeneral || whiteSahKontrol) && !oldSah) {
                                    System.out.println("hatali hareket");
                                    this.getBoard().getItems()[index].setPosition("to");
                                    this.getBoard().getItems()[i].setPosition(from);
                                    this.getBoard().setItemPositionsArrayPerItem();
                                    this.white.setPuan(this.white.getPuan() - this.returnPoint(itemOnNewPosition));
                                    this.turn = 1;
                                }
                                break;
                            }
                        }
                    }
                } else {
                    System.out.println("hatali hareket");
                }
            }
        } else {
            if (this.turn == 0) {
                System.out.println("Hamle sirasi " + this.getRed().getName() + " isimli red oyuncuya ait!");
            } else {
                System.out.println("hatali hareket");
            }
        }
        if (oldSah && whiteSahKontrol) {
            System.out.println("hatali hareket. SAH MAT! " + this.red.name + " oyunu kazandi. " + this.red.name
                    + "'in puani: " + this.red.puan + ", " + this.white.name + "'nin puani: " + this.white.puan);

        }
    }

    // Done
    public int[] findPositionOnItemPositionsArray(String from) { // returns vert and hori indeces on 10x9 array
        String position = from;
        int[] positions = new int[2];
        int horizontal = Integer.valueOf(position.charAt(1) - '0') - 1;
        positions[1] = horizontal;
        char verticalChar = position.charAt(0);
        int vertical = -1;
        switch (verticalChar) {
            case 'j':
                vertical = 0;
                break;
            case 'i':
                vertical = 1;
                break;
            case 'h':
                vertical = 2;
                break;
            case 'g':
                vertical = 3;
                break;
            case 'f':
                vertical = 4;
                break;
            case 'e':
                vertical = 5;
                break;
            case 'd':
                vertical = 6;
                break;
            case 'c':
                vertical = 7;
                break;
            case 'b':
                vertical = 8;
                break;
            case 'a':
                vertical = 9;
                break;
            default:
                break;
        }
        positions[0] = vertical;
        return positions;
    }

    // Done
    public boolean checkFlyingGeneralRule() { // true = flying general, false = keep playing
        Item redSah = this.getBoard().getItems()[4];
        Item whiteSah = this.getBoard().getItems()[20];
        int[] redSahPosition = findPositionOnItemPositionsArray(redSah.getPosition());
        int[] whiteSahPosition = findPositionOnItemPositionsArray(whiteSah.getPosition());
        int horizontal = 0;
        if (redSahPosition[1] != whiteSahPosition[1]) {
            return false;
        } else {
            horizontal = redSahPosition[1];
            for (int i = redSahPosition[0] + 1; i < whiteSahPosition[0]; i++) {
                if (!this.getBoard().getItemPositions()[i][horizontal].equals("-")) {
                    return false;
                }
            }
        }
        return true;
    }

    // Done
    public float returnPoint(String item) {
        float point = 0;
        String tmp = item.toLowerCase();
        switch (tmp) {
            case "k":
                point = 9;
                break;
            case "a":
                point = 4;
                break;
            case "f":
                point = 2;
                break;
            case "v":
                point = 2;
                break;
            case "ş":
                point = 5;
                break;
            case "t":
                point = 4.5f;
            case "p": // TODO: river gecince 2 oluyor
                point = 1;
                break;
            case "-":
                point = 0;
                break;
        }
        return point;
    }

    // Done
    void save_text(String address) {
        String text = this.getBoard().getBoardSituation() + "\n" + this.turn + " " + this.red.name + " " + this.red.puan
                + " " + this.white.name + " " +
                +this.white.puan + " " + this.redSahKontrol + " " + this.whiteSahKontrol;
        try {
            FileWriter myWriter = new FileWriter(address);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Done
    void load_text(String address) {
        String situation = "";
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(address));
            String line = reader.readLine();
            line = line.replace("\n", "");
            while (line != null) {
                situation += line + "\n";
                lines.add(line);
                line = reader.readLine();
            }
            situation = situation.substring(0, situation.lastIndexOf("\n")); // there should not be extra line for bot
            String strTurn = situation.substring(situation.lastIndexOf("\n"), situation.length());
            strTurn = strTurn.replace("\n", "");
            String turnPart = strTurn.substring(0, strTurn.indexOf(" "));
            this.turn = Integer.valueOf(turnPart);
            strTurn = strTurn.substring(strTurn.indexOf(" ") + 1);
            this.red.name = strTurn.substring(0, strTurn.indexOf(" "));
            strTurn = strTurn.substring(strTurn.indexOf(" ") + 1);
            this.red.puan = Float.valueOf(strTurn.substring(0, strTurn.indexOf(" ")));
            strTurn = strTurn.substring(strTurn.indexOf(" ") + 1);
            // red isim ve puan alindi
            this.white.name = strTurn.substring(0, strTurn.indexOf(" "));
            strTurn = strTurn.substring(strTurn.indexOf(" ") + 1);
            this.white.puan = Float.valueOf(strTurn.substring(0, strTurn.indexOf(" ")));
            strTurn = strTurn.substring(strTurn.indexOf(" ") + 1);
            // white isim ve puan alindi
            this.redSahKontrol = Boolean.valueOf(strTurn.substring(0, strTurn.indexOf(" ")));
            strTurn = strTurn.substring(strTurn.indexOf(" ") + 1);
            this.whiteSahKontrol = Boolean.valueOf(strTurn);
            situation = situation.substring(0, situation.lastIndexOf("\n")); // there should not be extra line for bot
            this.getBoard().setBoardSituation(situation);
            lines.remove(lines.size() - 1);
            this.getBoard().setBoardLines(lines);
            this.getBoard().setItemPositionsFromBoardSituation(getBoard().getBoardLines());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // done
    void save_binary(String address) {
        String text = this.getBoard().getBoardSituation() + "\n" + this.turn + " " + this.red.name + " " + this.red.puan
                + " " + this.white.name + " " +
                +this.white.puan + " " + this.redSahKontrol + " " + this.whiteSahKontrol;
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(address));
            oos.writeUTF(text);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Done
    void load_binary(String address) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(address));
            String situation = ois.readUTF();
            ArrayList<String> lines = new ArrayList<>();
            String strTurn = situation.substring(situation.lastIndexOf("\n"), situation.length());
            strTurn = strTurn.replace("\n", "");
            String turnPart = strTurn.substring(0, strTurn.indexOf(" "));
            this.turn = Integer.valueOf(turnPart);
            strTurn = strTurn.substring(strTurn.indexOf(" ") + 1);
            this.red.name = strTurn.substring(0, strTurn.indexOf(" "));
            strTurn = strTurn.substring(strTurn.indexOf(" ") + 1);
            this.red.puan = Float.valueOf(strTurn.substring(0, strTurn.indexOf(" ")));
            strTurn = strTurn.substring(strTurn.indexOf(" ") + 1);
            // red isim ve puan alindi
            this.white.name = strTurn.substring(0, strTurn.indexOf(" "));
            strTurn = strTurn.substring(strTurn.indexOf(" ") + 1);
            this.white.puan = Float.valueOf(strTurn.substring(0, strTurn.indexOf(" ")));
            strTurn = strTurn.substring(strTurn.indexOf(" ") + 1);
            // white isim ve puan alindi
            this.redSahKontrol = Boolean.valueOf(strTurn.substring(0, strTurn.indexOf(" ")));
            strTurn = strTurn.substring(strTurn.indexOf(" ") + 1);
            this.whiteSahKontrol = Boolean.valueOf(strTurn);
            situation = situation.substring(0, situation.lastIndexOf("\n")); // there should not be extra line for bot
            this.getBoard().setBoardSituation(situation);
            while (situation.indexOf("\n") != -1) {
                String newLine = situation.substring(0, situation.indexOf("\n"));
                newLine = newLine.replace("\n", "");
                situation = situation.substring(situation.indexOf("\n") + 1);
                lines.add(newLine);
            }
            lines.remove(lines.size() - 1);
            getBoard().setBoardLines(lines);
            getBoard().setItemPositionsFromBoardSituation(getBoard().getBoardLines());
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void isRedOnSah(int[] position, String item) {
        int v = position[0];
        int h = position[1];
        String[][] tmpArr = this.getBoard().getItemPositions();
        int vSah = 0;
        int hSah = 0;
        boolean isFound = false;
        for (int i = 0; i < 10; i++) { // red sah pozisyonu bulma
            for (int j = 0; j < 9; j++) {
                if (tmpArr[i][j].equals("ş")) {
                    vSah = i;
                    hSah = j;
                    isFound = true;
                    break;
                }
            }
            if (isFound) {
                break;
            }
        }
        switch (item) {
            case "P":
                if ((v + 1 == vSah && h == hSah) || (v == vSah && h + 1 == hSah) || (v == vSah && h - 1 == hSah)) {
                    this.redSahKontrol = true;
                }
                break;
            case "A":
                if (vSah <= 9) {
                    if (tmpArr[vSah - 1][hSah - 2].equals("A") && tmpArr[vSah - 1][hSah - 1].equals("-")) {
                        this.redSahKontrol = true;
                    } else if (tmpArr[vSah - 2][hSah - 1].equals("A") && tmpArr[vSah - 1][hSah - 1].equals("-")) {
                        this.redSahKontrol = true;
                    } else if (tmpArr[vSah - 2][hSah + 1].equals("A") && tmpArr[vSah - 1][hSah + 1].equals("-")) {
                        this.redSahKontrol = true;
                    } else if (tmpArr[vSah - 1][hSah + 2].equals("A") && tmpArr[vSah - 1][hSah + 1].equals("-")) {
                        this.redSahKontrol = true;
                    }
                }
                if (vSah <= 8) {
                    if (tmpArr[vSah + 1][hSah - 2].equals("A") && tmpArr[vSah + 1][hSah - 1].equals("-")) {
                        this.redSahKontrol = true;
                    } else if (tmpArr[vSah + 1][hSah + 2].equals("A") && tmpArr[vSah + 1][hSah + 1].equals("-")) {
                        this.redSahKontrol = true;
                    }
                }
                if (vSah <= 7) {
                    if (tmpArr[vSah + 2][hSah - 1].equals("A") && tmpArr[vSah + 1][hSah - 1].equals("-")) {
                        this.redSahKontrol = true;
                    } else if (tmpArr[vSah + 2][hSah + 1].equals("A") && tmpArr[vSah + 1][hSah + 1].equals("-")) {
                        this.redSahKontrol = true;
                    }
                }
                break;
            case "K":
                if (v == vSah) { // horizontal sah
                    if (Math.abs(hSah - h) == 1) {
                        this.redSahKontrol = true;
                        break;
                    } else if (h < hSah) { // from left to right
                        this.redSahKontrol = true;
                        for (int i = h + 1; i < hSah; i++) {
                            if (!tmpArr[v][i].equals("-")) {
                                this.redSahKontrol = false;
                                break;
                            }
                        }
                    } else if (h > hSah) { // from right to left
                        this.redSahKontrol = true;
                        for (int i = hSah + 1; i < h; i++) {
                            if (!tmpArr[v][i].equals("-")) {
                                this.redSahKontrol = false;
                                break;
                            }
                        }
                    }
                } else if (h == hSah) { // vertical sah
                    if (Math.abs(vSah - v) == 1) {
                        this.redSahKontrol = true;
                        break;
                    } else if (v < vSah) { // from top to bottom
                        this.redSahKontrol = true;
                        for (int i = v + 1; i < vSah; i++) {
                            if (!tmpArr[i][h].equals("-")) {
                                this.redSahKontrol = false;
                                break;
                            }
                        }
                    } else if (v > vSah) { // from bottom to top
                        this.redSahKontrol = true;
                        for (int i = vSah + 1; i < v; i++) {
                            if (!tmpArr[i][h].equals("-")) {
                                this.redSahKontrol = false;
                                break;
                            }
                        }
                    }
                }
                break;
            case "T":
                ArrayList<String> route = new ArrayList<>();
                int count = 0;
                if (v == vSah) { // horizontal sah
                    if (Math.abs(hSah - h) == 1) {
                        break;
                    } else if (h < hSah) { // from left to right
                        for (int i = h + 1; i < hSah; i++) {
                            route.add(tmpArr[v][i]);
                        }
                    } else if (h > hSah) { // from right to left
                        for (int i = hSah + 1; i < h; i++) {
                            route.add(tmpArr[v][i]);
                        }
                    }
                } else if (h == hSah) { // vertical sah
                    if (Math.abs(vSah - v) == 1) {
                        break;
                    } else if (v < vSah) { // from top to bottom
                        for (int i = v + 1; i < vSah; i++) {
                            route.add(tmpArr[i][h]);
                        }
                    } else if (v > vSah) { // from bottom to top
                        for (int i = vSah + 1; i < v; i++) {
                            route.add(tmpArr[i][h]);
                        }
                    }
                }
                if (route.size() != 0) {
                    for (int i = 0; i < route.size(); i++) {
                        if (!route.get(i).equals("-")) {
                            count++;
                        }
                    }
                }
                if (count == 1) {
                    this.redSahKontrol = true;
                }
                break;
            default:
                break;
        }
    }

    public void isWhiteOnSah(int[] position, String item) {
        int v = position[0];
        int h = position[1];
        String[][] tmpArr = this.getBoard().getItemPositions();
        int vSah = 0;
        int hSah = 0;
        boolean isFound = false;
        for (int i = 0; i < 10; i++) { // white sah pozisyonu bulma
            for (int j = 0; j < 9; j++) {
                if (this.getBoard().getItemPositions()[i][j].equals("Ş")) {
                    vSah = i;
                    hSah = j;
                    isFound = true;
                    break;
                }
            }
            if (isFound) {
                break;
            }
        }
        switch (item) {
            case "p":
                if ((v + -1 == vSah && h == hSah) || (v == vSah && h + 1 == hSah) || (v == vSah && h - 1 == hSah)) {
                    this.whiteSahKontrol = true;
                }
                break;
            case "a":
                if (vSah >= 0) {
                    if (tmpArr[vSah + 1][hSah - 2].equals("a") && tmpArr[vSah + 1][hSah - 1].equals("-")) {
                        this.whiteSahKontrol = true;
                    } else if (tmpArr[vSah + 2][hSah - 1].equals("a") && tmpArr[vSah + 1][hSah - 1].equals("-")) {
                        this.whiteSahKontrol = true;
                    } else if (tmpArr[vSah + 2][hSah + 1].equals("a") && tmpArr[vSah + 1][hSah + 1].equals("-")) {
                        this.whiteSahKontrol = true;
                    } else if (tmpArr[vSah + 1][hSah + 2].equals("a") && tmpArr[vSah + 1][hSah + 1].equals("-")) {
                        this.whiteSahKontrol = true;
                    }
                }
                if (vSah >= 1) {
                    if (tmpArr[vSah - 1][hSah - 2].equals("a") && tmpArr[vSah - 1][hSah - 1].equals("-")) {
                        this.whiteSahKontrol = true;
                    } else if (tmpArr[vSah - 1][hSah + 2].equals("a") && tmpArr[vSah - 1][hSah + 1].equals("-")) {
                        this.whiteSahKontrol = true;
                    }
                }
                if (vSah >= 2) {
                    if (tmpArr[vSah - 2][hSah - 1].equals("a") && tmpArr[vSah - 1][hSah - 1].equals("-")) {
                        this.whiteSahKontrol = true;
                    } else if (tmpArr[vSah - 2][hSah + 1].equals("a") && tmpArr[vSah - 1][hSah + 1].equals("-")) {
                        this.whiteSahKontrol = true;
                    }
                }
                break;
            case "k":
                if (v == vSah) { // horizontal sah
                    if (Math.abs(hSah - h) == 1) {
                        this.whiteSahKontrol = true;
                        break;
                    } else if (h < hSah) { // from left to right
                        this.whiteSahKontrol = true;
                        for (int i = h + 1; i < hSah; i++) {
                            if (!tmpArr[v][i].equals("-")) {
                                this.whiteSahKontrol = false;
                                break;
                            }
                        }
                    } else if (h > hSah) { // from right to left
                        this.whiteSahKontrol = true;
                        for (int i = hSah + 1; i < h; i++) {
                            if (!tmpArr[v][i].equals("-")) {
                                this.whiteSahKontrol = false;
                                break;
                            }
                        }
                    }
                } else if (h == hSah) { // vertical sah
                    if (Math.abs(vSah - v) == 1) {
                        this.whiteSahKontrol = true;
                        break;
                    } else if (v < vSah) { // from top to bottom
                        this.whiteSahKontrol = true;
                        for (int i = v + 1; i < vSah; i++) {
                            if (!tmpArr[i][h].equals("-")) {
                                this.whiteSahKontrol = false;
                                break;
                            }
                        }
                    } else if (v > vSah) { // from bottom to top
                        this.whiteSahKontrol = true;
                        for (int i = vSah + 1; i < v; i++) {
                            if (!tmpArr[i][h].equals("-")) {
                                this.whiteSahKontrol = false;
                                break;
                            }
                        }
                    }
                }
                break;
            case "t":
                ArrayList<String> route = new ArrayList<>();
                int count = 0;
                if (v == vSah) { // horizontal sah
                    if (Math.abs(hSah - h) == 1) {
                        break;
                    } else if (h < hSah) { // from left to right
                        for (int i = h + 1; i < hSah; i++) {
                            route.add(tmpArr[v][i]);
                        }
                    } else if (h > hSah) { // from right to left
                        for (int i = hSah + 1; i < h; i++) {
                            route.add(tmpArr[v][i]);
                        }
                    }
                } else if (h == hSah) { // vertical sah
                    if (Math.abs(vSah - v) == 1) {
                        break;
                    } else if (v < vSah) { // from top to bottom
                        for (int i = v + 1; i < vSah; i++) {
                            route.add(tmpArr[i][h]);
                        }
                    } else if (v > vSah) { // from bottom to top
                        for (int i = vSah + 1; i < v; i++) {
                            route.add(tmpArr[i][h]);
                        }
                    }
                }
                if (route.size() != 0) {
                    for (int i = 0; i < route.size(); i++) {
                        if (!route.get(i).equals("-")) {
                            count++;
                        }
                    }
                }
                if (count == 1) {
                    this.whiteSahKontrol = true;
                }
                break;
            default:
                break;
        }
    }

    // kirmizi tas oynadi ama sah verdi, hatali hareket pas, kirmizi tas oynadiktan
    // sonra flying general gibi yap
    public void checkYourMoveMadeSahForRed() {
        this.redSahKontrol = false;
        int[] position = new int[2];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.getBoard().getItemPositions()[i][j].equals("P")) {
                    position[0] = i;
                    position[1] = j;
                    isRedOnSah(position, "P");
                } else if (this.getBoard().getItemPositions()[i][j].equals("A")) {
                    position[0] = i;
                    position[1] = j;
                    isRedOnSah(position, "A");
                } else if (this.getBoard().getItemPositions()[i][j].equals("K")) {
                    position[0] = i;
                    position[1] = j;
                    isRedOnSah(position, "K");
                } else if (this.getBoard().getItemPositions()[i][j].equals("T")) {
                    position[0] = i;
                    position[1] = j;
                    isRedOnSah(position, "T");
                }
                if (this.redSahKontrol == true) {
                    break;
                }
            }
            if (this.redSahKontrol == true) {
                break;
            }
        }
    }

    // beyaz tas oynadi ama sah verdi, hatali hareket, beyaz tas oynadiktan
    // sonra flying general gibi yap
    public void checkYourMoveMadeSahForWhite() {
        this.whiteSahKontrol = false;
        int[] position = new int[2];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.getBoard().getItemPositions()[i][j].equals("p")) {
                    position[0] = i;
                    position[1] = j;
                    isWhiteOnSah(position, "p");
                } else if (this.getBoard().getItemPositions()[i][j].equals("a")) {
                    position[0] = i;
                    position[1] = j;
                    isWhiteOnSah(position, "a");
                } else if (this.getBoard().getItemPositions()[i][j].equals("k")) {
                    position[0] = i;
                    position[1] = j;
                    isWhiteOnSah(position, "k");
                } else if (this.getBoard().getItemPositions()[i][j].equals("t")) {
                    position[0] = i;
                    position[1] = j;
                    isWhiteOnSah(position, "t");
                }
                if (this.whiteSahKontrol == true) {
                    break;
                }
            }
            if (this.whiteSahKontrol == true) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Game g = new Game("A", "B");
        g.getBoard().print();
    }
}