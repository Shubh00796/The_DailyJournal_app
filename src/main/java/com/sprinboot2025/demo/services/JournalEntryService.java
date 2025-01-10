package com.sprinboot2025.demo.services;

import com.sprinboot2025.demo.entity.JournalEntry;
import com.sprinboot2025.demo.entity.UserEntry;
import com.sprinboot2025.demo.repo.JourenalenteryRepo;
import com.sprinboot2025.demo.repo.UserRepo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JourenalenteryRepo journalEntryRepo;

    @Autowired
    private UserRepo userRepo;


  private static final  Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    // Save a journal entry with an associated user
    @Transactional
    public JournalEntry saveJournalEntryWithUser(JournalEntry journalEntry) throws Exception {
        JournalEntry journalEntrys = new JournalEntry();
        journalEntry.setUser(null); // User is null
//        journalService.saveJournalEntryWithUser(journalEntry);

        if (journalEntry.getUser() == null || journalEntry.getUser().getEmail() == null) {
            logger.info("sjsjjsjsjsjjs");
            throw new Exception("User email is required");
        }

        // Fetch the user by email
        Optional<UserEntry> userOptional = userRepo.findByEmail(journalEntry.getUser().getEmail());
        if (!userOptional.isPresent()) {
            throw new Exception("User not found");
        }
        UserEntry user = userOptional.get();
        journalEntry.setUser(user); // Associate the user with the journal entry

        // Save the journal entry
        JournalEntry savedEntry = journalEntryRepo.save(journalEntry);

        // Add the saved journal entry to the user's journal entries
        user.getJournalEntries().add(savedEntry);
        userRepo.save(user); // Save the user with the updated journal entries

        // Remove unnecessary fields from the user before returning
        UserEntry userToReturn = savedEntry.getUser();
        userToReturn.setPassword(null); // Do not return password
        userToReturn.setJournalEntries(null); // Do not return journal entries in user

        savedEntry.setUser(userToReturn); // Set the user with the filtered fields

        return savedEntry;
    }

    public JournalEntry updateJournalEntry(String id, JournalEntry updatedEntry) throws Exception {
        // Fetch the existing journal entry by ObjectId
        Optional<JournalEntry> journalInDbOptional = journalEntryRepo.findById(new ObjectId(id));

        // Handle the case where no entry is found
        if (!journalInDbOptional.isPresent()) {
            throw new Exception("Journal Entry not found");
        }

        JournalEntry journalInDb = journalInDbOptional.get();

        // Update fields selectively
        if (updatedEntry.getTitle() != null) {
            journalInDb.setTitle(updatedEntry.getTitle());
        }
        if (updatedEntry.getContent() != null) {
            journalInDb.setContent(updatedEntry.getContent());
        }

        // Save and return the updated journal entry
        return journalEntryRepo.save(journalInDb);
    }


    // Get all journal entries
    public List<JournalEntry> getAllEntries() {
        return journalEntryRepo.findAll();
    }

    // Find a journal entry by ID
    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }

    // Delete a journal entry by ID
    public void deleteById(ObjectId id) {
        journalEntryRepo.deleteById(id);
    }

}
