package com.main.repository;

import com.main.entities.Contact;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class ContactsRepositoryImpl implements ContactsRepository {

    private final String csvPath;

    @Override
    public List<Contact> getContacts() {
        try {
            return readContacts();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private List<Contact> readContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            return reader.lines()
                    .map(this::mapToContact)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error reading contacts", e);
        }
    }

    @Contract("_ -> new")
    @NotNull
    private Contact mapToContact(@NotNull String csvLine) {
        // csv format: id,name,lastname,email,zipcode,address
        String[] fields = csvLine.split(",");
        return new Contact(
                Integer.parseInt(fields[0]),
                fields[1],
                fields[2],
                fields[3],
                fields[4].isEmpty() ? null : Integer.parseInt(fields[4]),
                fields[5]
        );
    }
}
