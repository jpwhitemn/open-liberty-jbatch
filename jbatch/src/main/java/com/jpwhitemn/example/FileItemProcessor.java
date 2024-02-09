package com.jpwhitemn.example;

import jakarta.batch.api.chunk.ItemProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named("fileItemProcessor")
@ApplicationScoped
public class FileItemProcessor implements ItemProcessor {
    @Override
    public Expectation processItem(Object o) throws Exception {
        Rating r = (Rating) o;
        try {
            if (Integer.parseInt(r.want()) > Integer.parseInt(r.score())) {
                return new Expectation("yes", r.match());
            } else {
                return new Expectation("no", r.match());
            }
        } catch (NumberFormatException e){
            return new Expectation("unk", r.match());
        }

    }
}
