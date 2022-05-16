package ru.netology.cloudstorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.netology.cloudstorage.service.StorageService;

@Component
public class CommandLineApp implements CommandLineRunner {
    private final StorageService storageService;

    @Autowired
    public CommandLineApp(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
