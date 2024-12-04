package com.main.matcher;

import com.main.entities.Accuracy;
import com.main.entities.Contact;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ContactsMatcherImpl implements ContactsMatcher {
    private Contact reference;
    private Contact candidate;

    @Override
    public Accuracy calculateMatch(@NotNull Contact reference, @NotNull Contact candidate) {
        this.reference = reference;
        this.candidate = candidate;

        if (sameId() || sameEmail()) return Accuracy.HIGH;
        if (similarFullName() && sameAddress()) return Accuracy.MEDIUM;
        if (similarFullName() && similarFullAddress()) return Accuracy.LOW;

        return null;
    }

    private boolean similarFullName() {
        return similarName() && similarLastname();
    }

    private boolean similarFullAddress() {
        return sameZipCode() || sameAddress();
    }

    private boolean sameId() {
        return reference.id() == candidate.id();
    }

    private boolean similarName() {
        if (reference.name() == null && candidate.name() == null) return true;
        if (reference.name() == null || candidate.name() == null) return false;
        return reference.name().startsWith(candidate.name()) || candidate.name().startsWith(reference.name());
    }

    private boolean similarLastname() {
        if (reference.lastname() == null && candidate.lastname() == null) return true;
        if (reference.lastname() == null || candidate.lastname() == null) return false;
        return reference.lastname().startsWith(candidate.lastname()) || candidate.lastname().startsWith(reference.lastname());
    }

    private boolean sameEmail() {
        if (reference.email() == null || candidate.email() == null) return false;
        return reference.email().equalsIgnoreCase(candidate.email());
    }

    private boolean sameZipCode() {
        return Objects.equals(reference.zipcode(), candidate.zipcode());
    }

    private boolean sameAddress() {
        if (reference.address() == null || candidate.address() == null) return false;
        String referenceAddress = reference.address().replace("Street", "St").toLowerCase();
        String candidateAddress = candidate.address().replace("Street", "St").toLowerCase();
        return referenceAddress.equals(candidateAddress);
    }
}
