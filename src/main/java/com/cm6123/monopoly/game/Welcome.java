package com.cm6123.monopoly.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * A simple class that generates a welcome message based on the time of day.
 */
public class Welcome {

    /**
     * Welcome text.
     */
    private static final String WELCOME_TEXT = "Welcome to Monopoly!";

    /**
     * Message for the morning.
     */
    private static final String MORNING = "Morning";
    /**
     * Message for the afternoon.
     */
    private static final String AFTERNOON = "Afternoon";

    /**
     * Create a logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Welcome.class);

    /**
     * The clock used to determine the time of day.
     */
    private final Clock internalClock;

    /**
     * Default constructor. Set the clock to the standard time zone.
     */
    public Welcome() {
        internalClock = Clock.systemDefaultZone();
    }

    /**
     * Constructor that allows the clock to be set to a specific time zone or to a mock clock for testing.
     * @param aClock
     */

    public Welcome(final Clock aClock) {
        internalClock = aClock;
    }

    /**
     * Generates a welcome message based on the time of day.
     * @return a time-sensitive welcome message.
     */
    public String welcomeMessage() {

        LocalDateTime now = LocalDateTime.now(internalClock);

        String timeOfDay;

        if (now.getHour() < 12) {
            timeOfDay = MORNING;
        } else {
            timeOfDay = AFTERNOON;
        }

        LOGGER.info("Timestamp : {}", now);


        return timeOfDay + ", " + WELCOME_TEXT;
    }
}
