// TODO MS PROJECT
package Console;

import Core.Card;
import Core.Game;
import Core.PlayerPhase;
import Core.PlayerPlace;
import java.util.Scanner;
import Core.IEngineInterface;

public class ConsoleGame implements IEngineInterface {

    private static final String INV_INP_MSSG = "Invalid, input, please only input numbers and commands, type 'help' for more info";

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
        game = new Game(this, playerNames);
        gameLoop();
    }

    private void gameLoop() {
        while (gameRunning) {
            for (byte i = 0; i < game.getPlayers().size(); i++) {
                if (game.getTurn() % 10 == 0 && game.getTurn() != 0) {
                    System.out.println("TURN " + game.getTurn());
                }
                underlineOut("Player " + game.getActivePlayer().getName() + "'s turn");

                while (game.getActivePlayer().getPhase() == PlayerPhase.PHASE_ACTION) {
                    actionPrint();
                    actionInput();
                }

                while (game.getActivePlayer().getPhase() == PlayerPhase.PHASE_BUY) {
                    buyPrint();
                    buyInput();
                }

                if (game.getActivePlayer().getPhase() == PlayerPhase.PHASE_CLEANUP) {
                    game.cleanup();
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
        System.out.println("ACTION PHASE");
        System.out.println("You can undertake this many actions: " + game.getActivePlayer().getActions());
        System.out.println("You have these action cards:");
        int i = 0;

        for (Card card : game.getActionCards()) {
            System.out.println(i++ + ". " + card.toString());
        }
    }

    private void actionInput() {
        System.out.print("Which card do you wish to play? > ");
        String input = in.next();
        try {
            if (!processInput(input)) {
                game.playActionCard(Integer.parseInt(input));
            }
        }
        catch (NumberFormatException e) {
            confirmMessage(INV_INP_MSSG);
        }
        catch (Exception e) {
            confirmMessage(e.getMessage());
        }
        
    }

    private void buyPrint() {
        System.out.println("\nBUY PHASE");
        System.out.println("Possible transactions: " + game.getActivePlayer().getBuys());
        System.out.println("You have these treasure cards:");
        int i = 1;

        for (Card card : game.getTreasureCards()) {
            System.out.println(i++ + ". " + card.toString());
        }

        printBuyableCards();
    }

    private void buyInput() {
        System.out.print("What do you wish to buy? > ");
        String input = in.next();
        try {
            if (!processInput(input)) {
                System.out.print("Which treasure cards will you use for this? (Cost:" + game.getCurrentSet().get(Integer.parseInt(input) - 1).getCost() + "): > ");
                in.nextLine();
                char[] cards = in.nextLine().toCharArray();
                game.buy(Integer.parseInt(input), processSpacedInput(cards));
            }
        } 
        catch (NumberFormatException e) {
            confirmMessage(INV_INP_MSSG);
        }
        catch (Exception e) {
            confirmMessage(e.getMessage());
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

    private void printHelp() {
        System.out.println("\nsupply - Print the supply");
        System.out.println("hand - Print you hand");
        System.out.println("skip - Go to next stage / player\n");
    }

    private boolean processInput(String input) { // false = keep current command; true = go to gameloop
        switch (input) {
            case "supply":
                printSupply();
                return true;
            case "hand":
                printHand();
                return true;
            case "help":
                printHelp();
                return true;
            case "skip":
                game.getActivePlayer().nextPhase();
                return true;
            case "exit":
                gameRunning = false;
                return true;
            default:
                return false;
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

    private void confirmMessage(String message) {
        System.out.println(message);
        System.out.print("\nPress ENTER to continue");
        try {
            System.in.read();
        } catch (Exception e) {
            // Nothing here
        }
    }

    @Override
    public String promptPlayer(String message) {
        System.out.print(message);
        return in.next();
    }

    @Override
    public void displayCards(Card[] cards) {
        int i = 1;
        for (Card card : cards) {
            System.out.println(i++ + ". " + card.toString());
        }
    }

    @Override
    public void messagePlayer(String message) {
        confirmMessage(message);
    }
}
