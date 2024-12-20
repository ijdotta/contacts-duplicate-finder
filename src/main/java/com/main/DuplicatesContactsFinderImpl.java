package com.main;

import com.main.entities.Contact;
import com.main.entities.Match;
import com.main.matcher.ContactsMatcher;
import com.main.repository.ContactsRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Finds duplicates contacts in the contacts repository.
 */
@RequiredArgsConstructor
public class DuplicatesContactsFinderImpl implements DuplicatesContactsFinder {
    @NotNull private final ContactsRepository repository;
    @NotNull private final ContactsMatcher matcher;
    @NotNull private final List<Match> matches = new ArrayList<>();

    @Override
    public List<Match> findDuplicates() {
        calculateMatches();
        removeExtraMatches();
        return matches;
    }

    private void calculateMatches() {
        List<Contact> contacts = repository.getContacts();
        for (int i = 0; i < contacts.size(); i++)
            for (int j = i + 1; j < contacts.size(); j++)
                tryToMatch(contacts.get(i), contacts.get(j));
    }

    private void tryToMatch(Contact reference, Contact candidate) {
        Optional.ofNullable(matcher.calculateMatch(reference, candidate))
                .map(accuracy -> new Match(reference.id(), candidate.id(), accuracy))
                .ifPresent(matches::add);
    }

    private void removeExtraMatches() {
        Set<Integer> matchesIDs = matches.stream().map(Match::matchId).collect(Collectors.toSet());
        matches.removeIf(match -> matchesIDs.contains(match.sourceId()));
    }
}
