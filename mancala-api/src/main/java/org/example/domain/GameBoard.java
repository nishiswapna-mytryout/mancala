package org.example.domain;

import lombok.Getter;
import org.example.datastructures.CircularLinkedList;
import org.example.datastructures.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class GameBoard {

    private static GameBoard activeGameBoard;

    private final Map<String, Pit> allPits;
    private final List<SmallPit> sideA;
    private final List<SmallPit> sideB;
    private final Map<String, String> gamePitMapping;
    private final CircularLinkedList<String> gamePitNavigation;
    private final BigPit bigPitA;
    private final BigPit bigPitB;

    public static GameBoard initializeGame(int gameStoneCount) {

        if (activeGameBoard == null) {
            activeGameBoard = new GameBoard(gameStoneCount);
        }
        return activeGameBoard;

    }


    private GameBoard(int gameStoneCount) {

        this.allPits = new HashMap<String, Pit>() {{
            put("A1", new SmallPit("A1", gameStoneCount));
            put("A2", new SmallPit("A2", gameStoneCount));
            put("A3", new SmallPit("A3", gameStoneCount));
            put("A4", new SmallPit("A4", gameStoneCount));
            put("A5", new SmallPit("A5", gameStoneCount));
            put("A6", new SmallPit("A6", gameStoneCount));
            put("AL", new BigPit("AL"));
            put("B1", new SmallPit("B1", gameStoneCount));
            put("B2", new SmallPit("B2", gameStoneCount));
            put("B3", new SmallPit("B3", gameStoneCount));
            put("B4", new SmallPit("B4", gameStoneCount));
            put("B5", new SmallPit("B5", gameStoneCount));
            put("B6", new SmallPit("B6", gameStoneCount));
            put("BL", new BigPit("BL"));
        }};

        this.gamePitMapping = new HashMap<String, String>() {{
            put("A1", "B6");
            put("A2", "B5");
            put("A3", "B4");
            put("A4", "B3");
            put("A5", "B2");
            put("A6", "B1");
            put("B1", "A6");
            put("B2", "A5");
            put("B3", "A4");
            put("B4", "A3");
            put("B5", "A2");
            put("B6", "A1");
        }};

        this.gamePitNavigation = new CircularLinkedList<>();
        this.gamePitNavigation.addNode("A1");
        this.gamePitNavigation.addNode("A2");
        this.gamePitNavigation.addNode("A3");
        this.gamePitNavigation.addNode("A4");
        this.gamePitNavigation.addNode("A5");
        this.gamePitNavigation.addNode("A6");
        this.gamePitNavigation.addNode("AL");
        this.gamePitNavigation.addNode("B1");
        this.gamePitNavigation.addNode("B2");
        this.gamePitNavigation.addNode("B3");
        this.gamePitNavigation.addNode("B4");
        this.gamePitNavigation.addNode("B5");
        this.gamePitNavigation.addNode("B6");
        this.gamePitNavigation.addNode("BL");


        this.sideA = this.allPits.entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains("A") && entry.getValue() instanceof SmallPit)
                .map(entry -> (SmallPit) entry.getValue())
                .collect(Collectors.toList());
        this.sideB = this.allPits.entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains("B") && entry.getValue() instanceof SmallPit)
                .map(entry -> (SmallPit) entry.getValue())
                .collect(Collectors.toList());

        this.bigPitA = (BigPit) this.allPits.get("AL");
        this.bigPitB = (BigPit) this.allPits.get("BL");
    }


    /**
     * This provided the opposite small pit of the current position
     *
     * @param pitPosition
     * @return SmallPit
     * @throws IllegalArgumentException
     */
    public SmallPit getOppositePit(String pitPosition) {
        if (!this.gamePitMapping.containsKey(pitPosition)) {
            throw new IllegalArgumentException(String.format("Wrong Pit position %s", pitPosition));

        }
        String oppositePitPosition = this.gamePitMapping.get(pitPosition);
        return (SmallPit) allPits.get(oppositePitPosition);
    }


    /**
     * This method provides the next pit based on the player turn.
     * In case player turn is A then it will give all the pits in counter clockwise, but will skip
     * opponents big pit
     *
     * @param playerTurn
     * @param pitPosition
     * @return Pit
     * @throws IllegalArgumentException
     */
    public Pit getNextPit(PlayerTurn playerTurn, String pitPosition) {

        if (pitPosition == null || pitPosition.trim().length() == 0) {
            throw new IllegalArgumentException("Pit Position cannot be null or empty");
        }

        Node<String> currentPosition = gamePitNavigation.getNode(pitPosition);

        if (currentPosition == null) {
            throw new IllegalArgumentException("Current Position cannot be null or empty, issue with navigation setup");
        }

        Node<String> nextNode = currentPosition.getNextNode();

        if ((PlayerTurn.PLAYER_A.equals(playerTurn)
                && "BL".equals(nextNode.getValue())) || (PlayerTurn.PLAYER_B.equals(playerTurn)
                && "AL".equals(nextNode.getValue()))) {
            //move to the next pit
            nextNode = nextNode.getNextNode();

        }

        return allPits.get(nextNode.getValue());
    }

    @Override
    public String toString() {
        String sideA = this.sideA.stream().map(pits-> pits.getPitPosition()+": "+pits.getCurrentStoneCount()).collect(Collectors.joining(","));
        sideA= sideA+", "+this.bigPitA.getPitPosition()+": "+this.bigPitA.getCurrentStoneCount();
        String sideB = this.sideB.stream().map(pits-> pits.getPitPosition()+": "+pits.getCurrentStoneCount()).collect(Collectors.joining(","));
        sideB= sideB+", "+this.bigPitB.getPitPosition()+": "+this.bigPitB.getCurrentStoneCount();
        return "\r\n"+sideA+"\r\n"+sideB;
    }
}
