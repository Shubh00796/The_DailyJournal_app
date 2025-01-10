package com.sprinboot2025.demo.repo;

import com.sprinboot2025.demo.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<UserEntry, ObjectId> {
    Optional<UserEntry> findByEmail(String email); // Use UserEntry here
    Optional<UserEntry> findByUsername(String username);
}
