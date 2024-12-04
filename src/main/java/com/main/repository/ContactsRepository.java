package com.main.repository;

import com.main.entities.Contact;

import java.util.List;

public interface ContactsRepository {
    List<Contact> getContacts();
}
