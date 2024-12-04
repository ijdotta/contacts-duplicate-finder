package com.main.repository;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.main.entities.Contact;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log
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

    @Getter @Setter
    private static class ContactModel {
        private String contactID;
        private String name;
        private String name1;
        private String email;
        private Integer postalZip;
        private String address;
    }

    private List<Contact> readContacts() {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema()
                .withHeader()
                .withColumnSeparator(',')
                .withQuoteChar('"')
                .withLineSeparator("\n");

        try (FileReader reader = new FileReader(csvPath)) {
            return mapper.readerFor(ContactModel.class)
                    .with(schema)
                    .readValues(reader)
                    .readAll()
                    .stream()
                    .map(ContactModel.class::cast)
                    .map(this::mapToContact)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Contract("_ -> new")
    @NotNull
    private Contact mapToContact(@NotNull ContactModel model) {
        return new Contact(
                Integer.parseInt(model.contactID),
                model.name.isEmpty() ? null : model.name,
                model.name1.isEmpty() ? null : model.name1,
                model.email.isEmpty() ? null : model.email,
                model.postalZip,
                model.address.isEmpty() ? null : model.address
        );
    }
}
