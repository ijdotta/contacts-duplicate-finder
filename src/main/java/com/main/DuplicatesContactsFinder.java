package com.main;

import com.main.entities.Match;

import java.util.List;

public interface DuplicatesContactsFinder {
    List<Match> findDuplicates();
}
