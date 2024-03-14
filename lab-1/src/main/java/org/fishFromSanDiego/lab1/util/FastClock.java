package org.fishFromSanDiego.lab1.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class FastClock implements Clock {
    private final long _timeSpeedUpCoefficient;
    private LocalDateTime _previousRealDateTime;
    private LocalDateTime _previousFastDateTime;

    public FastClock(int timeSpeedUpCoefficient, LocalDateTime initialDateTime) {
        _timeSpeedUpCoefficient = timeSpeedUpCoefficient;
        _previousRealDateTime = LocalDateTime.now();
        _previousFastDateTime = initialDateTime;
    }

    @Override
    public LocalDateTime getCurrentDateTime() {
        var currentRealDateTime = LocalDateTime.now();
        Duration diff =
                Duration.between(_previousRealDateTime, currentRealDateTime).multipliedBy(_timeSpeedUpCoefficient);
        _previousFastDateTime = _previousFastDateTime.plus(diff);
        _previousRealDateTime = currentRealDateTime;
        return _previousFastDateTime;
    }
}
