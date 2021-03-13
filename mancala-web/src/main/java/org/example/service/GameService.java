package org.example.service;

import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class GameService {

    private static String PlayerA;

    private static String PlayerB;

    private static int defaultpitCount = 6;

    public Date getTime() {
        return new Date();
    }

    public void setPlayer(String id, String name) {
        if("A".equalsIgnoreCase(id)){
            PlayerA = name;
        }
        else if("B".equalsIgnoreCase(id)){
            PlayerB = name;
        }
    }

    public String getPlayerA() {
        return PlayerA;
    }

    public String getPlayerB() {
        return PlayerB;
    }

    public String getPitcount(String pitIndex) {

        // to-do: logic to fetch from mancala-api
        return String.valueOf(defaultpitCount);
    }

    public void sow(String pitIndex){}

}
