package com.cm6123.monopoly;

import com.cm6123.monopoly.game.Welcome;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WelcomeTests {

    @Test
    public void testWelcomeMessageInTheMorning() {

        Clock mockClock = mock(Clock.class);
        when (mockClock.instant()).thenReturn(Instant.parse("2025-01-01T10:00:00Z"));
        when (mockClock.getZone()).thenReturn(ZoneId.of("UTC"));

        Welcome welcome = new Welcome(mockClock);

        assert(welcome.welcomeMessage().equals("Morning, Welcome to Monopoly!"));

    }

    @Test
    public void testWelcomeMessageInTheAfternoon() {

        Clock mockClock = mock(Clock.class);
        when (mockClock.instant()).thenReturn(Instant.parse("2025-01-01T13:00:00Z"));
        when (mockClock.getZone()).thenReturn(ZoneId.of("UTC"));

        Welcome welcome = new Welcome(mockClock);

        assert(welcome.welcomeMessage().equals("Afternoon, Welcome to Monopoly!"));

    }

}
