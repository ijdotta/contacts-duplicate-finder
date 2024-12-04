package com.main.repository;

import com.main.entities.Contact;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactsRepositoryTest {

    @Test
    void whenNonEmptyInputFileShouldReturnContacts() {
        ContactsRepository contactsRepository = new ContactsRepositoryImpl("src/test/resources/contacts.csv");
        List<Contact> contacts = contactsRepository.getContacts();
        assertNotNull(contacts, "Contacts list should not be null");
        assertNotEquals(0, contacts.size(), "Contacts list should not be empty");
    }

    @Test
    void whenEmptyInputFileShouldReturnEmptyContacts() {
        ContactsRepository contactsRepository = new ContactsRepositoryImpl("src/test/resources/contacts_empty.csv");
        List<Contact> contacts = contactsRepository.getContacts();
        assertNotNull(contacts, "Contacts list should not be null");
        assertEquals(0, contacts.size(), "Contacts list should be empty");
    }

    @Test
    void whenNonExistentInputFileShouldReturnEmptyContacts() {
        ContactsRepository contactsRepository = new ContactsRepositoryImpl("nothing");
        List<Contact> contacts = contactsRepository.getContacts();
        assertNotNull(contacts, "Contacts list should not be null");
        assertEquals(0, contacts.size(), "Contacts list should be empty");
    }
}