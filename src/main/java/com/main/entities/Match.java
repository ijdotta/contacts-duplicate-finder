package com.main.entities;

public record Match(
        int sourceId,
        int matchId,
        Accuracy accuracy
) { }
