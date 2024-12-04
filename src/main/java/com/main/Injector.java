package com.main;

import com.main.matcher.ContactsMatcher;
import com.main.matcher.ContactsMatcherImpl;
import com.main.repository.ContactsRepository;
import com.main.repository.ContactsRepositoryImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Injector {

    static class Holder {
        static final Injector INSTANCE = new Injector();
    }

    public static Injector getInjector() {
        return Holder.INSTANCE;
    }

    private static final String FILE_PATH = "src/main/resources/contacts.csv";
    private final ContactsRepository repository = new ContactsRepositoryImpl(FILE_PATH);
    private final ContactsMatcher matcher = new ContactsMatcherImpl();
    @Getter private final DuplicatesContactsFinder duplicatesContactsFinder = new DuplicatesContactsFinderImpl(repository, matcher);
}
