package com.sprinboot2025.demo.repo;

import com.sprinboot2025.demo.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JourenalenteryRepo extends MongoRepository<JournalEntry, ObjectId> {
    // You can add custom query methods here if needed
}
