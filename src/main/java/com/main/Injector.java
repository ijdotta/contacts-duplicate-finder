package com.main;

import com.main.matcher.ContactsMatcher;
import com.main.matcher.ContactsMatcherImpl;
import com.main.output.*;
import com.main.repository.ContactsRepository;
import com.main.repository.ContactsRepositoryImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Injector {

    static class Holder {
        static final Injector INSTANCE = new Injector();
    }

    public static Injector getInstance() {
        return Holder.INSTANCE;
    }

    private static final String INPUT_FILE_PATH = "src/main/resources/contacts.csv";
    private final ContactsRepository repository = new ContactsRepositoryImpl(INPUT_FILE_PATH);
    private final ContactsMatcher matcher = new ContactsMatcherImpl();
    @Getter private final DuplicatesContactsFinder duplicatesContactsFinder = new DuplicatesContactsFinderImpl(repository, matcher);

    private static final String OUTPUT_FILE_PATH = "src/main/resources/duplicates.csv";
    @Getter private final ContactsDuplicatesWriter contactsDuplicatesWriter = new ContactsDuplicatesWriterImpl(OUTPUT_FILE_PATH);
}
