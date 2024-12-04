package com.main;

public class Main {
    public static void main(String[] args) {
        Injector.getInstance().getDuplicatesContactsFinder().findDuplicates().forEach(System.out::println);
    }
}