package com.jpwhitemn.example;

import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import jakarta.batch.api.chunk.AbstractItemReader;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.*;
import java.util.stream.Stream;

@Named("fileItemReader")
@ApplicationScoped
public class FileItemReader extends AbstractItemReader {

    // from https://www.openml.org/search?type=data&sort=runs&status=active&id=40536
    private static final String filename = "speeddating.csv";

    private FileReader reader;
    private CSVReader csvReader;

    @Override
    public Object readItem() throws Exception {
        String[] line;
        line = csvReader.readNext();
        if (line != null) {
            return new Rating(line[51], line[61], line[122]);
        }
        return null;
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {
        System.out.println("Opening CSV for stream");
        reader = new FileReader("speeddating.csv");
        csvReader = new CSVReader(reader);
        csvReader.skip(128);
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing CSV file");
        csvReader.close();
        reader.close();
    }
}
