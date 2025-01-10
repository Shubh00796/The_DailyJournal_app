package com.sprinboot2025.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "journal_entries")
public class JournalEntry {

    @Id
    private ObjectId id; // MongoDB ObjectId
    @NonNull
    private String title; // Title of the journal entry
    @NonNull
    private String content; // Content of the journal entry
    private String createdAt; // Created timestamp
    private String updatedAt; // Updated timestamp

    @JsonBackReference  // Child side of the relationship
    @DBRef
    private UserEntry user;  // Reference back to UserEntry

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserEntry getUser() {
        return user;
    }

    public void setUser(UserEntry user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "JournalEntry{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", user=" + user +
                '}';
    }
}
