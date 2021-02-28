package org.example.domain;

import lombok.NonNull;


public class BigPit extends Pit{

    public BigPit(@NonNull final String pitPosition) {
        super(pitPosition, 0);
    }

    @Override
    public boolean equals(Object pit) {
        return pit instanceof BigPit && this.getPitPosition().equals(((BigPit) pit).getPitPosition());
    }
}
