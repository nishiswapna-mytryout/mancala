package org.example.domain;

import lombok.Getter;
import lombok.NonNull;
import org.example.datastructures.CircularLinkedList;
import org.example.datastructures.Node;
import org.example.domain.game.core.model.PlayerSide;
import org.example.domain.game.core.model.exceptions.GameIllegalStateException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.domain.game.core.model.PitBehavior.isBigPit;
import static org.example.domain.game.core.model.PitBehavior.validatePitPosition;


public class GameBoardFeatures {

    @Getter
    private final int stoneCountPerPit;


    /**
     * GamePit Mapping for opposite pit capture
     */
    private final Map<String, String> gamePitMapping;
    /**
     * GamePit Navigation for sowing right in counter clockwise direction
     */
    private final CircularLinkedList<String> gamePitNavigation;


    /**
     * GamePit Mapping for opposite pit capture
     */
    private final Map<PlayerSide, List<String>> pitSideMapping;

    public GameBoardFeatures(final int stoneCountPerPit) {

        this.stoneCountPerPit = stoneCountPerPit;

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

        pitSideMapping = new HashMap<PlayerSide, List<String>>() {{
            put(PlayerSide.SideA, Arrays.asList("A1", "A2", "A3", "A4", "A5", "A6", "AL"));
            put(PlayerSide.SideB, Arrays.asList("B1", "B2", "B3", "B4", "B5", "B6", "BL"));
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
     * @return Opposite Pit Position
     * @throws IllegalArgumentException
     */
    public String getOppositePitPosition(final String pitPosition) {
        validatePitPosition(pitPosition);

        if (!this.gamePitMapping.containsKey(pitPosition)) {
            throw new IllegalArgumentException(String.format("Wrong Pit position %s", pitPosition));

        }
        return this.gamePitMapping.get(pitPosition);
    }

    public String getNextPit(final String pitPosition, final PlayerSide playingSide) {
        validatePitPosition(pitPosition);
        final Node<String> currentPosition = gamePitNavigation
                .getNodeElseThrow(pitPosition, () -> new IllegalArgumentException("Current Position cannot be null or empty, issue with navigation setup"));

        String nextPitPosition = currentPosition.getNextNode().getValue();

        if (isBigPit(nextPitPosition)
                && !pitPositionBelongsToPlayingSide(nextPitPosition, playingSide)) {
            return currentPosition.getNextNode()
                    .getNextNode()
                    .getValue();
        } else {
            return nextPitPosition;
        }
    }

    public boolean pitPositionBelongsToPlayingSide(@NonNull final String pitPosition, @NonNull final PlayerSide playingSide) {
        return pitSideMapping.get(playingSide).contains(pitPosition);
    }

    public String getBigPitForPlayingSide(@NonNull final PlayerSide movingPlayerSide) {
        return pitSideMapping.get(movingPlayerSide)
                .stream()
                .filter(pitPosition -> pitPosition.contains("L"))
                .findFirst().orElseThrow(() -> new GameIllegalStateException("No Big Pit configured"));
    }
}
