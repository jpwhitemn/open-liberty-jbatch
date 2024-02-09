package com.jpwhitemn.example;

import jakarta.batch.api.chunk.AbstractItemWriter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Named("fileItemWriter")
@ApplicationScoped
public class FileItemWriter extends AbstractItemWriter {

    FileWriter myWriter;
    @Override
    public void writeItems(List<Object> list) throws Exception {
        System.out.println("Writing expectations");

        int metAndMatch = 0;
        int metAndNoMatch = 0;
        int noData = 0;
        try {
            myWriter = new FileWriter("results.txt", false);
            Object[] x = list.toArray();
            for(Object o: x) {
                Expectation e = (Expectation) o;
                if (e.met().equals("unk"))
                    noData++;
                if (e.met().equals("yes") && e.match().equals("0"))
                    metAndNoMatch++;
                if (e.met().equals("yes") && e.match().equals("1"))
                    metAndMatch++;
                myWriter.write(e.toString()+ "\n");
            }
            myWriter.write("Totals\nMet and match - " + metAndMatch);
            myWriter.write(("\nMet and no match - " + metAndNoMatch));
            myWriter.write("\nNo Data - " + noData + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred writing the expectations.");
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing write file");
        myWriter.close();
        super.close();
    }
}
