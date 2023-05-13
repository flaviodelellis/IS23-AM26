package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.localModel.ModelView;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.client.localModel.*;

import static it.polimi.ingsw.util.Constants.*;

public class CLI {

    // Metodi
    public void showLogo() {
        System.out.println(LOGO1);
    }

    public void showBoard(localBoard board) {
        StringBuilder table = new StringBuilder();

        for (int i = nRowBoard - 1; i > -2; i--) {
            switch (i) {
                case 8 -> table.append(" ₈ ");
                case 7 -> table.append(" ₇ ");
                case 6 -> table.append(" ₆ ");
                case 5 -> table.append(" ₅ ");
                case 4 -> table.append(" ₄ ");
                case 3 -> table.append(" ₃ ");
                case 2 -> table.append(" ₂ ");
                case 1 -> table.append(" ₁ ");
                case 0 -> table.append(" ₀ ");
            }
            for (int j = 0; j < nColumnBoard; j++) {
                if (i >= 0) {
                    if (board.board[i][j] == null) {
                        table.append(ANSI_RESET + BLACK_BACKGROUND + "   ");
                    } else {
                        switch (board.board[i][j].getType().getValue()) {
                            case 0 -> table.append(GREEN_BACKGROUND + "   ");
                            case 1 -> table.append(YELLOW_BACKGROUND + "   ");
                            case 2 -> table.append(BLUE_BACKGROUND + "   ");
                            case 3 -> table.append(PINK_BACKGROUND + "   ");
                            case 4 -> table.append(CYAN_BACKGROUND + "   ");
                            case 5 -> table.append(WHITE_BACKGROUND + "   ");
                        }
                    }
                } else {
                    switch (j) {
                        case 0 -> table.append("    ₀ ");
                        case 1 -> table.append(" ₁ ");
                        case 2 -> table.append(" ₂ ");
                        case 3 -> table.append(" ₃ ");
                        case 4 -> table.append(" ₄ ");
                        case 5 -> table.append(" ₅ ");
                        case 6 -> table.append(" ₆ ");
                        case 7 -> table.append(" ₇ ");
                        case 8 -> table.append(" ₈ ");
                    }
                }
            }
            table.append(ANSI_RESET + "\n");
        }
        System.out.println(table);
    }

    public void showBookshelf(localBookshelf bookshelf) {
        StringBuilder library = new StringBuilder();

        library.append("libreria di " +bookshelf.name+ "\n");
        for (int i = nRowBookshelf - 1; i > -3; i--) {
            for (int j = 0; j < nColumnBookshelf; j++) {
                if (i >= 0) {
                    if (j == 0) {
                        library.append(ANSI_RESET + "   │");
                    }
                    if (bookshelf.bookshelf[i][j] == null) {
                        library.append(ANSI_RESET + DEFAULT_BACKGROUND + "   ");
                        library.append(ANSI_RESET + "│");
                    } else {
                        switch (bookshelf.bookshelf[i][j].getType().getValue()) {
                            case 0 -> library.append(GREEN_BACKGROUND + "   ");
                            case 1 -> library.append(YELLOW_BACKGROUND + "   ");
                            case 2 -> library.append(BLUE_BACKGROUND + "   ");
                            case 3 -> library.append(PINK_BACKGROUND + "   ");
                            case 4 -> library.append(CYAN_BACKGROUND + "   ");
                            case 5 -> library.append(WHITE_BACKGROUND + "   ");
                        }
                        library.append(ANSI_RESET + "│");
                    }
                } else if (i == -1) {
                    if (j == 0) {
                        library.append("   └───┴");
                    } else if (j == 4) {
                        library.append("───┘");
                    } else {
                        library.append("───┴");
                    }
                } else {
                    switch (j) {
                        case 0 -> library.append("     ⁰ ");
                        case 1 -> library.append("  ¹ ");
                        case 2 -> library.append("  ² ");
                        case 3 -> library.append("  ³ ");
                        case 4 -> library.append("  ⁴ ");
                    }
                }
            }
            library.append(ANSI_RESET + "\n");
        }
        System.out.println(library);
    }

    public void showHand(localHand hand) {
        System.out.print("\n");
        System.out.println("mano:");
        for(Item item: hand.hand)
            System.out.print(item.getType().getValue() + " ");
        System.out.print("\n");
    }

    public void showPersonalCard() {

    }

    // Questa funzione dovrebbe mostrare a video le due CommonCard corrette
    public void showCommonCard(int Index) {
        switch (Index) {
            case 1 -> System.out.println(commonCard1());
            case 2 -> System.out.println(commonCard2());
            case 3 -> System.out.println(commonCard3());
            case 4 -> System.out.println(commonCard4());
            case 5 -> System.out.println(commonCard5());
            case 6 -> System.out.println(commonCard6());
            case 7 -> System.out.println(commonCard7());
            case 8 -> System.out.println(commonCard8());
            case 9 -> System.out.println(commonCard9());
            case 10 -> System.out.println(commonCard10());
            case 11 -> System.out.println(commonCard11());
            case 12 -> System.out.println(commonCard12());
        }
    }

    private String commonCard1() {
        String commonCard1 = " ┌─────────────────┐ \n" +
                " │                 │ \n" +
                " │                 │ \n" +
                " │       " + BLACK_BACKGROUND + " = " + ANSI_RESET + "       │ \n" +
                " │       " + BLACK_BACKGROUND + " = " + ANSI_RESET + "       │ \n" +
                " │        x6       │ \n" +
                " │                 │ \n" +
                " └─────────────────┘ \n";

        return commonCard1;
    }

    private String commonCard2() {
        String commonCard2 = " ┌─────────────────┐ \n" +
                " │                 │ \n" +
                " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "          │ \n" +
                " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "          │ \n" +
                " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "  x4      │ \n" +
                " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "          │ \n" +
                " │                 │ \n" +
                " └─────────────────┘ \n";
        return commonCard2;
    }

    private String commonCard3() {
        String commonCard3 = " ┌─────────────────┐ \n" +
                " │                 │ \n" +
                " │ " + BLACK_BACKGROUND + " = " + ANSI_RESET + " -  -  - " + BLACK_BACKGROUND + " = " + ANSI_RESET + " │ \n" +
                " │  |           |  │ \n" +
                " │  |           |  │ \n" +
                " │  |           |  │ \n" +
                " │ " + BLACK_BACKGROUND + " = " + ANSI_RESET + " -  -  - " + BLACK_BACKGROUND + " = " + ANSI_RESET + " │ \n" +
                " └─────────────────┘ \n";
        return commonCard3;
    }

    private String commonCard4() {
        String commonCard4 = " ┌─────────────────┐ \n" +
                " │                 │ \n" +
                " │                 │ \n" +
                " │      " + BLACK_BACKGROUND + " = = " + ANSI_RESET + "      │ \n" +
                " │      " + BLACK_BACKGROUND + " = = " + ANSI_RESET + "      │ \n" +
                " │       x2        │ \n" +
                " │                 │ \n" +
                " └─────────────────┘ \n";
        return commonCard4;
    }

    private String commonCard5() {
        String commonCard5 = " ┌─────────────────┐ \n" +
                " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "             │ \n" +
                " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "             │ \n" +
                " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "   max 3 " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + " │ \n" +
                " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "             │ \n" +
                " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "   x3        │ \n" +
                " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "             │ \n" +
                " └─────────────────┘ \n";
        return commonCard5;
    }

    private String commonCard6() {
        String commonCard6 = " ┌─────────────────┐ \n" +
                " │                 │ \n" +
                " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + "    │ \n" +
                " │                 │ \n" +
                " │ " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + " │ \n" +
                " │                 │ \n" +
                " │ " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + " │ \n" +
                " └─────────────────┘ \n";
        return commonCard6;
    }

    private String commonCard7() {
        String commonCard7 = " ┌─────────────────┐ \n" +
                " │                 │ \n" +
                " │ " + BLACK_BACKGROUND + " = " + ANSI_RESET + "             │ \n" +
                " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "          │ \n" +
                " │       " + BLACK_BACKGROUND + " = " + ANSI_RESET + "       │ \n" +
                " │          " + BLACK_BACKGROUND + " = " + ANSI_RESET + "    │ \n" +
                " │             " + BLACK_BACKGROUND + " = " + ANSI_RESET + " │ \n" +
                " └─────────────────┘ \n";
        return commonCard7;
    }

    private String commonCard8() {
        String commonCard8 = " ┌─────────────────┐ \n" +
                " │                 │ \n" +
                " │ " + BLACK_BACKGROUND + "               " + ANSI_RESET + " │ \n" +
                " │        x4       │ \n" +
                " │                 │ \n" +
                " │    max 3 " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "    │ \n" +
                " │                 │ \n" +
                " └─────────────────┘ \n";
        return commonCard8;
    }

    private String commonCard9() {
        String commonCard9 = " ┌─────────────────┐ \n" +
                " │    " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "          │ \n" +
                " │    " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "          │ \n" +
                " │    " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "          │ \n" +
                " │    " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "  x2      │ \n" +
                " │    " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "          │ \n" +
                " │    " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "          │ \n" +
                " └─────────────────┘ \n";
        return commonCard9;
    }

    private String commonCard10() {
        String commonCard10 = " ┌─────────────────┐ \n" +
                " │                 │ \n" +
                " │                 │ \n" +
                " │ " + BLACK_BACKGROUND + " ≠  ≠  ≠  ≠  ≠ " + ANSI_RESET + " │ \n" +
                " │        x2       │ \n" +
                " │                 │ \n" +
                " │                 │ \n" +
                " └─────────────────┘ \n";
        return commonCard10;
    }

    private String commonCard11() {
        String commonCard11 = " ┌─────────────────┐ \n" +
                " │                 │ \n" +
                " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + "    │ \n" +
                " │       " + BLACK_BACKGROUND + " = " + ANSI_RESET + "       │ \n" +
                " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + "    │ \n" +
                " │                 │ \n" +
                " │                 │ \n" +
                " └─────────────────┘ \n";
        return commonCard11;
    }

    private String commonCard12() {
        String commonCard12 = " ┌─────────────────┐ \n" +
                " │                 │ \n" +
                " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "             │ \n" +
                " │ " + BLACK_BACKGROUND + "      " + ANSI_RESET + "          │ \n" +
                " │ " + BLACK_BACKGROUND + "         " + ANSI_RESET + "       │ \n" +
                " │ " + BLACK_BACKGROUND + "            " + ANSI_RESET + "    │ \n" +
                " │ " + BLACK_BACKGROUND + "               " + ANSI_RESET + " │ \n" +
                " └─────────────────┘ \n";
        return commonCard12;
    }

}