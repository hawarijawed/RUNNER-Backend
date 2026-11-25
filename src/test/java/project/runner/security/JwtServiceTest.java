package project.runner.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private UserDetails userDetails; // Fake UserDetails to isolate the user logic

    // A valid HS256 secret key must be at least 256 bits (32 bytes) long.
    // This is a Base64 encoded string of a 32-byte secure key.
    private final String testSecretKey = Base64.getEncoder().encodeToString(
            "super-secret-key-that-must-be-at-least-32-bytes-long!".getBytes()
    );
    private final long testExpiration = 3600000; // 1 hour in milliseconds

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        // Spring utility to manually inject the @Value fields into the service instance
        ReflectionTestUtils.setField(jwtService, "secretKey", testSecretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", testExpiration);
    }

    @Test
    void shouldGenerateAndExtractTokenSuccessfully() {
        // Arrange: Mock the behavior of UserDetails
        when(userDetails.getUsername()).thenReturn("testuser@example.com");

        // Act: Generate the token
        String token = jwtService.generateToken(userDetails);

        // Assert: Ensure token is created and verify claims extraction
        assertNotNull(token);

        String extractedEmail = jwtService.extractEmail(token);
        assertEquals("testuser@example.com", extractedEmail);
    }

    @Test
    void shouldValidateCorrectToken() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("testuser@example.com");
        String token = jwtService.generateToken(userDetails);

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void shouldInvalidateTokenWhenUsernamesDoNotMatch() {
        // Arrange
        when(userDetails.getUsername()).thenReturn("realuser@example.com");
        String token = jwtService.generateToken(userDetails);

        // Mock a different user attempting to validate this token
        //@Mock
        UserDetails wrongUser = org.mockito.Mockito.mock(UserDetails.class);
        when(wrongUser.getUsername()).thenReturn("hacker@example.com");

        // Act
        boolean isValid = jwtService.isTokenValid(token, wrongUser);

        // Assert
        assertFalse(isValid);
    }
}