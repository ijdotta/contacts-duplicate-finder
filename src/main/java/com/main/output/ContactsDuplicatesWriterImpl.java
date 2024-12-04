package com.main.output;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.main.entities.Accuracy;
import com.main.entities.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Log
@RequiredArgsConstructor
public class ContactsDuplicatesWriterImpl implements ContactsDuplicatesWriter {
    private final String outputFilePath;

    @AllArgsConstructor
    @Getter @Setter
    static class ContactDuplicateModel {
        int contactID_Source;
        int contactID_Match;
        Accuracy accuracy;
    }

    @Override
    public void writeDuplicates(List<Match> duplicates) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(ContactDuplicateModel.class).withHeader();
        List<ContactDuplicateModel> duplicatesAsModel = getDuplicatesAsContactDuplicateModel(duplicates);
        try {
            mapper.writer(schema).writeValue(new File(outputFilePath), duplicatesAsModel);
        } catch (IOException e) {
            log.severe("Failed to write duplicates to file: " + outputFilePath);
        }
    }

    private List<ContactDuplicateModel> getDuplicatesAsContactDuplicateModel(@NotNull List<Match> duplicates) {
        return duplicates.stream()
                .map(duplicate -> new ContactDuplicateModel(duplicate.sourceId(), duplicate.matchId(), duplicate.accuracy()))
                .toList();
    }
}
