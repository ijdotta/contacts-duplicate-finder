package com.main.output;

import com.main.entities.Match;

import java.util.List;

/**
 * Interface for exporting duplicates to a database.
 */
public interface ContactsDuplicatesWriter {
    void writeDuplicates(List<Match> duplicates);
}
