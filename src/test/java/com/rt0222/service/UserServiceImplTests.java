package com.rt0222.service;

import com.rt0222.domain.model.Clerk;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceImplTests {
    private final UserService userService;

    @Test()
    @Order(1)
    public void testGetNonExistentClerk() {
        // Throw error, clerk doesn't exist
        EmptyResultDataAccessException responseStatusException = Assertions.assertThrows(EmptyResultDataAccessException.class, () -> userService.getClerk(2L));

        // Make sure error has nice message
        Assertions.assertTrue(Objects.requireNonNull(responseStatusException.getMessage()).contains("Clerk \"2\" not found."));
    }

    @Test()
    @Order(2)
    public void testUserServiceCreatesATimestampForClerk() {
        // Create a clerk
        Timestamp before = Timestamp.from(Instant.now());
        Clerk clerk = userService.createClerk();
        Assertions.assertEquals(2, clerk.getId());
        Timestamp createdAt = clerk.getCreatedAt();
        Assertions.assertTrue(createdAt.after(before) || before.equals(createdAt));

        // Attempt to retrieve the same clerk we just created
        clerk = userService.getClerk(2L);
        Assertions.assertEquals(2, clerk.getId());
    }

    @Test()
    @Order(3)
    public void testCreateAndGetAClerk() {
        // Create a clerk
        Clerk clerk = userService.createClerk();
        Assertions.assertEquals(3, clerk.getId());

        // Attempt to retrieve the same clerk we just created
        clerk = userService.getClerk(3L);
        Assertions.assertEquals(3, clerk.getId());
    }
}
