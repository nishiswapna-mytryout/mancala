package org.example.domain;

import lombok.NonNull;


public class SmallPit extends Pit{

    public SmallPit(@NonNull final String pitPosition, @NonNull final int currentStoneCount) {
        super(pitPosition, currentStoneCount);
    }



    public int pick(){

        if(this.getCurrentStoneCount()==0){
            throw new IllegalArgumentException("Pit is already empty, pick another pit");
        }

        int pickedUpStones = this.getCurrentStoneCount();
        this.setCurrentStoneCount(0);
        return pickedUpStones;

    }

    @Override
    public boolean equals(Object pit) {
        return pit instanceof SmallPit && this.getPitPosition().equals(((SmallPit) pit).getPitPosition());
    }

}
