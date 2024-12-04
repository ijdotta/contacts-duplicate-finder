package com.main.matcher;

import com.main.entities.Accuracy;
import com.main.entities.Contact;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Models a matcher for contacts. Different implementations can define different matching strategies.
 */
public interface ContactsMatcher {
    /**
     * Calculates the match between two contacts.
     *
     * @param reference the reference contact
     * @param candidate the candidate contact
     * @return the accuracy of the match or null if they don't match
     */
    @Nullable Accuracy calculateMatch(@NotNull Contact reference, @NotNull Contact candidate);
}
