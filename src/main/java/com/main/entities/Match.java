package com.main.entities;

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
