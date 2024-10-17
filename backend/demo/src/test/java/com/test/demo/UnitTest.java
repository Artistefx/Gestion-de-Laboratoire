package com.test.demo;

import com.test.demo.Client.TestController;
import com.test.demo.Client.TestService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Enable Mockito in JUnit 5
public class UnitTest {

    @Mock
    private TestService myService; // Mocking the service

    @InjectMocks
    private TestController myController; // Inject the mock service into the controller

    @Test
    public void testGetHelloUnit() {
        // Arrange: Define behavior for the mocked service
        when(myService.getMessage()).thenReturn("Hello Mockito");

        // Act: Call the controller method directly
        ResponseEntity<String> response = myController.getHello();

        // Assert: Check the response
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // Check HTTP status
        assertThat(response.getBody()).isEqualTo("Hello Mockito"); // Check the returned message
    }
}
