package org.example.domain.interfaces;

public interface PitBehavior {

    static void validatePitPosition(final String pitPosition) {
        if (pitPosition == null || pitPosition.trim().length() == 0) {
            throw new IllegalArgumentException("Pit Position cannot be null or empty");
        }
    }


}
