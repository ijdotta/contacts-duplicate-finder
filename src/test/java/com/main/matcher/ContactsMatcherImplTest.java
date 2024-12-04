package com.main.matcher;

import com.main.entities.Accuracy;
import com.main.entities.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ContactsMatcherImplTest {

    private ContactsMatcherImpl matcher;

    @BeforeEach
    void setUp() {
        matcher = new ContactsMatcherImpl();
    }

    @Test
    void testCalculateMatch_highAccuracy_sameId() {
        Contact reference = new Contact(1, "John", "Doe", "john.doe@example.com", 12345, "123 Main St");
        Contact candidate = new Contact(1, "Jane", "Smith", "jane.smith@example.com", 67890, "456 Elm St");
        assertEquals(Accuracy.HIGH, matcher.calculateMatch(reference, candidate));
    }

    @Test
    void testCalculateMatch_highAccuracy_sameEmail() {
        Contact reference = new Contact(1, "John", "Doe", "common@example.com", 12345, "123 Main St");
        Contact candidate = new Contact(2, "Jane", "Smith", "common@example.com", 67890, "456 Elm St");
        assertEquals(Accuracy.HIGH, matcher.calculateMatch(reference, candidate));
    }

    @Test
    void testCalculateMatch_mediumAccuracy_sameFullNameAndSimilarFullAddress() {
        Contact reference = new Contact(1, "John", "Doe", "john.doe@example.com", 12345, "123 Main St");
        Contact candidate = new Contact(2, "John", "Doe", "jane.smith@example.com", 12345, "123 Main Street");
        assertEquals(Accuracy.MEDIUM, matcher.calculateMatch(reference, candidate));
    }

    @Test
    void testCalculateMatch_lowAccuracy_similarFullNameAndSimilarFullAddress() {
        Contact reference = new Contact(1, null, null, "john.doe@example.com", 12345, "123 Main St");
        Contact candidate = new Contact(2, null, null, "jane.smith@example.com", 12345, "123 Main Street");
        assertEquals(Accuracy.LOW, matcher.calculateMatch(reference, candidate));
    }

    @Test
    void testCalculateMatch_noMatch() {
        Contact reference = new Contact(1, "John", "Doe", "john.doe@example.com", 12345, "123 Main St");
        Contact candidate = new Contact(2, "Jane", "Smith", "jane.smith@example.com", 67890, "456 Elm St");
        assertNull(matcher.calculateMatch(reference, candidate));
    }

    @Test
    void testCalculateMatch_nullReferenceName() {
        Contact reference = new Contact(1, null, "Doe", "john.doe@example.com", 12345, "123 Main St");
        Contact candidate = new Contact(2, "Jane", "Smith", "jane.smith@example.com", 67890, "456 Elm St");
        assertNull(matcher.calculateMatch(reference, candidate));
    }

    @Test
    void testCalculateMatch_nullCandidateName() {
        Contact reference = new Contact(1, "John", "Doe", "john.doe@example.com", 12345, "123 Main St");
        Contact candidate = new Contact(2, null, "Smith", "jane.smith@example.com", 67890, "456 Elm St");
        assertNull(matcher.calculateMatch(reference, candidate));
    }

    @Test
    void testCalculateMatch_sameZipCodeButNoOtherMatch() {
        Contact reference = new Contact(1, "John", "Doe", "john.doe@example.com", 12345, "123 Main St");
        Contact candidate = new Contact(2, "Jane", "Smith", "jane.smith@example.com", 12345, "789 Another St");
        assertNull(matcher.calculateMatch(reference, candidate));
    }
}