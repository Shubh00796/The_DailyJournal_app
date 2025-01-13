package com.sprinboot2025.demo.cache;

import com.sprinboot2025.demo.repo.ConfigJournalAppRepository;
import com.sprinboot2025.demo.utility.ConfigJournalAppEntity;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
@Slf4j
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init() {
            List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
            for (ConfigJournalAppEntity configJournalAppEntity : all) {
                APP_CACHE.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
                log.info("Loaded key: {} with value: {}", configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
            }
        }

    }

