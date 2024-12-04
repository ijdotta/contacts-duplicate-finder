package com.main.matcher;

import com.main.entities.Accuracy;
import com.main.entities.Contact;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ContactsMatcherImpl implements ContactsMatcher {
    private Contact reference;
    private Contact candidate;

    @Override
    public Accuracy calculateMatch(@NotNull Contact reference, @NotNull Contact candidate) {
        this.reference = reference;
        this.candidate = candidate;

        if (sameId() || sameEmail()) return Accuracy.HIGH;
        if (sameFullName() && similarFullAddress()) return Accuracy.HIGH;
        if (similarFullName() && sameFullAddress()) return Accuracy.HIGH;
        if (similarFullName() && similarFullAddress()) return Accuracy.MEDIUM;
        if (similarFullName() || similarFullAddress()) return Accuracy.LOW;

        return null;
    }

    private boolean sameFullName() {
        return sameName() && sameLastName();
    }

    private boolean similarFullName() {
        return (reference.name().startsWith(candidate.name()) || candidate.name().startsWith(reference.name())) &&
                (reference.lastname().startsWith(candidate.lastname()) || candidate.lastname().startsWith(reference.lastname()));
    }

    private boolean sameFullAddress() {
        return sameZipCode() && sameAddress();
    }

    private boolean similarFullAddress() {
        return Objects.equals(reference.zipcode(), candidate.zipcode()) ||
                reference.address().contains(candidate.address()) ||
                candidate.address().contains(reference.address());
    }

    private boolean sameId() {
        return reference.id() == candidate.id();
    }

    private boolean sameName() {
        return reference.name().equalsIgnoreCase(candidate.name());
    }

    private boolean sameLastName() {
        return reference.lastname().equalsIgnoreCase(candidate.lastname());
    }

    private boolean sameEmail() {
        return reference.email().equalsIgnoreCase(candidate.email());
    }

    private boolean sameZipCode() {
        return Objects.equals(reference.zipcode(), candidate.zipcode());
    }

    private boolean sameAddress() {
        return reference.address().equalsIgnoreCase(candidate.address());
    }
}
