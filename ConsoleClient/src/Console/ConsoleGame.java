package Console;

import Core.Card;
import Core.Game;
import Core.PlayerPlace;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleGame {

    private boolean gameRunning = true;
    private Game game;
    Scanner in = new Scanner(System.in);

    public void init() {
        byte playerAmount;
        String[] playerNames = null;
        Scanner in = new Scanner(System.in);
        underlineOut("Welcome to dominion");

        System.out.print("Please choose the amount of players: ");
        playerAmount = Byte.parseByte(in.nextLine());

        while (playerAmount < 2 || playerAmount > 6) {
            String message = playerAmount > 6 ? "Please choose less than 7 players: " : "Please choose more than 2 players: ";
            System.out.print(message);
            playerAmount = Byte.parseByte(in.nextLine());
        }

        playerNames = new String[playerAmount];
        for (int i = 0; i < playerAmount; i++) {
            System.out.print("Player " + (i + 1) + " name: ");
            playerNames[i] = in.nextLine();
        }
        game = new Game(playerNames);
        gameLoop();
    }

    private void gameLoop() {
        while (gameRunning) {
            for (byte i = 0; i < game.getPlayers().size(); i++) {
                if (game.getTurn() % 10 == 0) {
                    System.out.println("TURN " + game.getTurn());
                    underlineOut("Player " + game.getActivePlayer().getName() + "'s turn");
                    actionPrint();
                    game.getActivePlayer().nextPhase();
                    buyPrint();
                    game.getActivePlayer().nextPhase();
                    game.nextPlayer();
                }
            }
        }
    }

    private void underlineOut(String message) {
        System.out.println("\n" + message);
        for (int i = 0; i < message.length() - 1; i++) {
            System.out.print("-");
        }
        System.out.println("-");
    }

    private void actionPrint() {
        if (game.getActivePlayer().hasActionCards(PlayerPlace.PLACE_HAND) && game.getActivePlayer().getActions() > 0) {
            System.out.println("ACTION PHASE");
            System.out.println("You can undertake this many actions: " + game.getActivePlayer().getActions());
            System.out.println("You have these action cards:");
            int i = 0;

            for (Card card : game.getActivePlayer().getActionCards(PlayerPlace.PLACE_HAND)) {
                System.out.println(i++ + ". " + card.toString());
            }

            System.out.print("Which card do you wish to play? ");
            while (!processInput(in.nextLine())) {
                System.out.print("What do you wish to do? ");
                processInput(in.nextLine());
            }
        }
    }

    private void buyPrint() {
        System.out.println("BUY PHASE");
        System.out.println("Possible transactions: " + game.getActivePlayer().getBuys());
        System.out.println("You have these treasure cards:");
        int i = 1;

        for (Card card : game.getActivePlayer().getTreasureCards(PlayerPlace.PLACE_HAND)) {
            System.out.println(i++ + ". " + card.toString());
        }

        printBuyableCards();

        while (game.getActivePlayer().getBuys() > 0 && game.getActivePlayer().getTreasury() > 0) {
            System.out.print("What do you wish to buy? ");
            while (!processInput(in.nextLine())) {
                System.out.print("What do you wish to buy? ");
            }
        }
    }

    private void printBuyableCards() {
        System.out.println("\nYou can buy these cards: ");
        int i = 1;
        for (Card card : game.getBuyableCards()) {
            System.out.println(i++ + ". " + card.toString() + " - COST: " + card.getCost() + " - " + game.getSupply().getCardAmount(card) + " left");
        }
    }

    private void printSupply() {
        System.out.println("\nThis is the supply:");
        for (Card card : game.getSupply().getActionCards()) {
            System.out.println(card.toString() + " - COST: " + card.getCost() + " - " + game.getSupply().getCardAmount(card) + " left");
        }
        System.out.println();
        for (Card card : game.getSupply().getVictoryCards()) {
            System.out.println(card.toString() + " - COST: " + card.getCost() + " - " + game.getSupply().getCardAmount(card) + " left");
        }
        System.out.println();
        for (Card card : game.getSupply().getTreasureCards()) {
            System.out.println(card.toString() + " - COST: " + card.getCost() + " - " + game.getSupply().getCardAmount(card) + " left");
        }
    }

    private void printHand() {
        System.out.println("\nThis is your hand: ");
        for (Card card : game.getActivePlayer().getHand()) {
            System.out.println(card.toString());
        }
    }

    private boolean processInput(String input) {
        switch (input) {
            case "supply":
                printSupply();
                return false;
            case "hand":
                printHand();
                return false;
            case "help":
                System.out.println("supply, hand");
                return false;
            case "skip":
                return true;
            default:
                try {
                    return processGameInput(input);
                } catch (Exception e) {
                    System.out.println("Not a valid command");
                    return false;
                }
        }
    }

    private boolean processGameInput(String input) throws Exception {
        switch (game.getActivePlayer().getPhase()) {
            case PHASE_ACTION:
                return false;
            case PHASE_BUY:
                System.out.print("Which treasure cards will you use for this? (ex. 1 2 4): ");
                try {
                    game.buy(Integer.parseInt(input), processSpacedInput(in.nextLine().toCharArray()));
                    return true;
                } catch (Exception e) {
                    System.out.println("Not enough money");
                    return false;
                }

            case PHASE_CLEANUP:
                return false;
            default:
                throw new Exception("unknown command");
        }
    }

    private int[] processSpacedInput(char[] in) {
        int characters = 0;
        int currentStart = 0;
        int[] temp = new int[in.length];
        String current = null;
        for (int i = 0; i < in.length; i++) {
            if (in[i] == ' ' || i == in.length - 1) {
                current = "";
                current += in[currentStart];
                for (int i2 = currentStart + 1; i2 < i; i2++) {
                    current += in[i2];
                }
                currentStart = i + 1;
                temp[characters++] = (Integer.parseInt(current));
            }
        }
        int[] out = new int[characters];
        int e = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != 0) {
                out[e++] = temp[i];
            }
        }
        return out;
    }
}
