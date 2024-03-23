package org.fishFromSanDiego.lab1.util;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The type Fast clock.
 */
public class FastClock implements Clock {
    private final long _timeSpeedUpCoefficient;
    private LocalDateTime _previousRealDateTime;
    private LocalDateTime _previousFastDateTime;

    /**
     * Instantiates a new Fast clock.
     *
     * @param timeSpeedUpCoefficient the time speed up coefficient
     * @param initialDateTime        the initial date time
     */
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
