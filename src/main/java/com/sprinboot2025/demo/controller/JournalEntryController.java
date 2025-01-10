package com.sprinboot2025.demo.controller;

import com.sprinboot2025.demo.entity.JournalEntry;
import com.sprinboot2025.demo.entity.UserEntry;
import com.sprinboot2025.demo.repo.JourenalenteryRepo;
import com.sprinboot2025.demo.services.JournalEntryService;
import com.sprinboot2025.demo.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @Autowired
    private JourenalenteryRepo journalEntryRepo;

    // Save a journal entry with an associated user
    @PostMapping("/save")
    public ResponseEntity<JournalEntry> saveJournalEntryWithUser(@RequestBody JournalEntry journalEntry) {
        try {
            JournalEntry savedEntry = journalEntryService.saveJournalEntryWithUser(journalEntry);
            return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntry(
            @PathVariable String id,
            @RequestBody JournalEntry updatedEntry) {
        try {
            // Call the service method to update the journal entry
            JournalEntry updatedJournalEntry = journalEntryService.updateJournalEntry(id, updatedEntry);
            return new ResponseEntity<>(updatedJournalEntry, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exception if the journal entry is not found
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    // Get all journal entries
    @GetMapping("/all")
    public ResponseEntity<List<JournalEntry>> getAllEntries() {
        List<JournalEntry> entries = journalEntryService.getAllEntries();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    // Get a journal entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable("id") ObjectId id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();

        Optional<JournalEntry> entry = journalEntryService.findById(id);

        if (entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a journal entry by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEntryById(@PathVariable("id") ObjectId id) {
        try {
            journalEntryService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-user-email/{email}")
    public ResponseEntity<List<JournalEntry>> getJournalEntriesByUserEmail(@PathVariable String email) {
        Optional<UserEntry> userOptional = userService.getUserByEmail(email);
        if (userOptional.isPresent()) {
            UserEntry user = userOptional.get();
            List<JournalEntry> allEntries = user.getJournalEntries();
            if (allEntries != null && !allEntries.isEmpty()) {
                return new ResponseEntity<>(allEntries, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No journal entries found
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
    }

}
