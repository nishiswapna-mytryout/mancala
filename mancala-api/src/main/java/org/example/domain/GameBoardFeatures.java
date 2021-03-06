package org.example.domain;

import lombok.Getter;
import org.example.datastructures.CircularLinkedList;
import org.example.datastructures.Node;
import org.example.domain.interfaces.PitBehavior;

import java.util.HashMap;
import java.util.Map;


public class GameBoardFeatures {

    private static GameBoardFeatures gameBoardFeatures;


    public static GameBoardFeatures initializeGame(int gameStoneCount) {

        if (gameBoardFeatures == null) {
            gameBoardFeatures = new GameBoardFeatures(gameStoneCount);
        }
        return gameBoardFeatures;

    }

    /**
     * The mapping between pit position and Actual Pits
     */
    @Getter
    private final Map<String, Pit> allPits;
    /**
     * GamePit Mapping for oppsoite pit capture
     */
    private final Map<String, String> gamePitMapping;
    /**
     * GamePit Navigation for sowing right in counter clockwise direction
     */
    private final CircularLinkedList<String> gamePitNavigation;

    private GameBoardFeatures(int gameStoneCount) {

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
    }

    /**
     * This provided the opposite small pit of the current position
     *
     * @param pitPosition
     * @return SmallPit
     * @throws IllegalArgumentException
     */
    public SmallPit getOppositePit(String pitPosition) {
        PitBehavior.validatePitPosition(pitPosition);

        if (!this.gamePitMapping.containsKey(pitPosition)) {
            throw new IllegalArgumentException(String.format("Wrong Pit position %s", pitPosition));

        }
        String oppositePitPosition = this.gamePitMapping.get(pitPosition);
        return (SmallPit) allPits.get(oppositePitPosition);
    }

    public Pit getNextPit(String pitPosition) {
        PitBehavior.validatePitPosition(pitPosition);
        final Node<String> currentPosition = gamePitNavigation
                .getNodeElseThrow(pitPosition, () -> new IllegalArgumentException("Current Position cannot be null or empty, issue with navigation setup"));
        return allPits.get(currentPosition.getNextNode().getValue());
    }

}
