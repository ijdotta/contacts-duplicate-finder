package com.main;

import com.main.entities.Match;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Match> duplicates = Injector.getInstance().getDuplicatesContactsFinder().findDuplicates();
        Injector.getInstance().getContactsDuplicatesWriter().writeDuplicates(duplicates);
    }
}