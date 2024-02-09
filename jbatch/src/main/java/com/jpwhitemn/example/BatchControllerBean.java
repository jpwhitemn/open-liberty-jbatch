package com.jpwhitemn.example;

import jakarta.annotation.PostConstruct;
import jakarta.batch.operations.JobOperator;
import jakarta.batch.runtime.BatchRuntime;
import jakarta.batch.runtime.JobExecution;
import jakarta.batch.runtime.JobInstance;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class BatchControllerBean {

    record JobStatus (String name, String id, String status){
    }

    private JobOperator operator;
    private List<JobStatus> stats;

    public JobOperator getOperator() {
        return operator;
    }

    public void setOperator(JobOperator operator) {
        this.operator = operator;
    }

    @PostConstruct
    public void initializeOperator() {
        System.out.println("Initializing the Batch job operator");
        operator = BatchRuntime.getJobOperator();
    }

    public void startJob1() {
        System.out.println("Initiating batch job #1");
        long execID = operator.start("datingStatsChunk", null);
        System.out.println("Batch job " + operator.getJobInstance(execID).getJobName() + ":" + execID + " has been started");
    }

    public void startJob2() {
        System.out.println("Initiating batch job #2");
        long execID = operator.start("factoralBatchlet", null);
        System.out.println("Batch job " + operator.getJobInstance(execID).getJobName() + ":" + execID + " has been started");
    }

    public List<JobStatus> status() {
        stats = new ArrayList<>();
        if (!operator.getJobNames().isEmpty()) {
            for(String name: operator.getJobNames()){
                List<JobInstance> instances = operator.getJobInstances(name, 0, Integer.MAX_VALUE);
                getStatusForJobInstances(instances);
            }
        }
        return stats;
    }

    private void getStatusForJobInstances(List<JobInstance> instances){
        if (!instances.isEmpty()) {
            Iterator<JobInstance> it = instances.iterator();
            JobInstance x;
            JobExecution ex;
            JobStatus s;
            while (it.hasNext()) {
                x = it.next();
                ex = operator.getJobExecution(x.getInstanceId());
                s = new JobStatus(x.getJobName(), "" + x.getInstanceId(), ex.getBatchStatus().toString());
                stats.add(s);
                //System.out.println(x.getJobName() + " - " + x.getInstanceId() + " is: " + ex.getBatchStatus());
            }
        }
    }

}