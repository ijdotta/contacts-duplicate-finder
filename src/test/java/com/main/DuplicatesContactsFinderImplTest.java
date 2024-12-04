package com.main;

import com.main.entities.Accuracy;
import com.main.entities.Contact;
import com.main.matcher.ContactsMatcher;
import com.main.repository.ContactsRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class DuplicatesContactsFinderImplTest {

    private final ContactsRepository contactsRepository = mock(ContactsRepository.class);
    private final ContactsMatcher contactsMatcher = mock(ContactsMatcher.class);
    private final DuplicatesContactsFinder duplicatesContactsFinder = new DuplicatesContactsFinderImpl(contactsRepository, contactsMatcher);
    private final List<Contact> contacts = new ArrayList<>();

    @BeforeEach
    void setContacts() {
        for (int i = 0; i < 100; i++) contacts.add(emptyContactWithId(i));
    }

    @Contract("_ -> new")
    private static @NotNull Contact emptyContactWithId(int id) {
        return new Contact(id, null, null, null, null, null);
    }

    @Test
    void whenEmptyContactsShouldReturnEmptyDuplicates() {
        when(contactsRepository.getContacts()).thenReturn(Collections.emptyList());
        assertTrue(duplicatesContactsFinder.findDuplicates().isEmpty());
    }

    @Test
    void whenNoDuplicatesShouldReturnEmptyDuplicates() {
        when(contactsRepository.getContacts()).thenReturn(contacts);
        when(contactsMatcher.calculateMatch(any(), any())).thenReturn(null);
        assertTrue(duplicatesContactsFinder.findDuplicates().isEmpty());
    }

    @Test
    void whenDuplicatesShouldReturnDuplicates() {
        when(contactsRepository.getContacts()).thenReturn(contacts);
        when(contactsMatcher.calculateMatch(any(), any())).thenReturn(Accuracy.HIGH);
        assertFalse(duplicatesContactsFinder.findDuplicates().isEmpty());
    }

    @Test
    void whenNotAllAreDuplicatesShouldReturnOnlyActualDuplicates() {
        when(contactsRepository.getContacts()).thenReturn(contacts);
        when(contactsMatcher.calculateMatch(contacts.getFirst(), contacts.get(1))).thenReturn(Accuracy.HIGH);
        when(contactsMatcher.calculateMatch(contacts.get(2), contacts.get(3))).thenReturn(Accuracy.MEDIUM);
        when(contactsMatcher.calculateMatch(contacts.get(4), contacts.get(5))).thenReturn(Accuracy.LOW);
        assertEquals(3, duplicatesContactsFinder.findDuplicates().size());
    }

    @Test
    void neverCalculateMatchForSameContacts() {
        when(contactsRepository.getContacts()).thenReturn(contacts);
        when(contactsMatcher.calculateMatch(any(), any())).thenReturn(Accuracy.HIGH);
        for (Contact contact : contacts) verify(contactsMatcher, never()).calculateMatch(contact, contact);
    }
}