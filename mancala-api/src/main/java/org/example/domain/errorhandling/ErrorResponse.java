package org.example.domain.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetTime;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private final String message;
    private final OffsetTime errorTime;

}
