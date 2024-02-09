package com.jpwhitemn.example;

import jakarta.batch.api.AbstractBatchlet;
import jakarta.batch.runtime.BatchStatus;

import java.math.BigInteger;

public class FactoralBatchlet extends AbstractBatchlet {

    final private static int n = 100000;
//    @Inject
//    StepContext stepContext;

    @Override
    public String process() {
        System.out.println("Computing the factorials up to: " + n);
        BigInteger fact = new BigInteger("1");
        for (long i = 1; i < n; i++) {
            BigInteger b2 = new BigInteger("" + i);
            fact = fact.multiply(b2);
            //System.out.println("Factorial of " + i + " is " + fact);
        }
        return BatchStatus.COMPLETED.toString();
    }
}