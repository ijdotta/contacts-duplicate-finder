package com.main.matcher;

import com.main.entities.Accuracy;
import com.main.entities.Contact;

/**
 * Models a matcher for contacts. Different implementations can define different matching strategies.
 */
public interface ContactsMatcher {
    Accuracy calculateMatch(Contact reference, Contact candidate);
}
