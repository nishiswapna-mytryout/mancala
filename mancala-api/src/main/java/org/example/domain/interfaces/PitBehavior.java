package org.example.domain.interfaces;

import java.io.Serializable;

public interface PitBehavior extends Serializable {

    static void validatePitPosition(final String pitPosition) {
        if (pitPosition == null || pitPosition.trim().length() == 0) {
            throw new IllegalArgumentException("Pit Position cannot be null or empty");
        }
    }


}
