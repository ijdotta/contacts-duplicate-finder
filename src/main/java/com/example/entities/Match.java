package com.example.entities;

enum Accuracy {
    LOW,
    MEDIUM,
    HIGH
}

public record Match(
        int sourceId,
        int matchId,
        Accuracy accuracy
) { }
