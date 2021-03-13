package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    private static final int defaultpitCount = 6;
    private static final String PlayerA = "A";
    private static final String PlayerB = "B";
    private static String playerA_name;
    private static String playerB_name;


    public void setPlayer(String id, String name) {
        if(PlayerA.equalsIgnoreCase(id)){
            playerA_name = name;
        }
        else if(PlayerB.equalsIgnoreCase(id)){
            playerB_name = name;
        }
        else throw new IllegalArgumentException("Invalid Player");
    }

    public String getPlayerA() {
        return playerA_name;
    }

    public String getPlayerB() {
        return playerB_name;
    }

    public String getPitcount(String pitIndex) {

        // to-do: logic to fetch from mancala-api
        return String.valueOf(defaultpitCount);
    }

    public void sow(String pitIndex){}

}
