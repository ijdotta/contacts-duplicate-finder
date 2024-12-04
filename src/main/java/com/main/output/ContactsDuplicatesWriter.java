package com.main.output;

import com.main.entities.Match;

import java.util.List;

public interface ContactsDuplicatesWriter {
    void writeDuplicates(List<Match> duplicates);
}
