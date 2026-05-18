package com.tcs.ilp.servease.controller;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tcs.ilp.servease.bo.ServiceCompletionBO;
import com.tcs.ilp.servease.entity.ServiceCompletion;
import com.tcs.ilp.servease.exception
        .ServiceCompletionExceptionMain.ServiceCompletionNotFoundException;

@Component
public class ServiceCompletionMain implements CommandLineRunner {

    private final ServiceCompletionBO bo;

    public ServiceCompletionMain(ServiceCompletionBO bo) {
        this.bo = bo;
    }

    @Override
    public void run(String... args) {

        System.out.println("Running ServiceCompletionMain...");

        String completionId1 = "co1";
        String completionId2 = "co2";
        String serviceId = "s1";

        try {
            bo.deleteServiceCompletion(completionId1);
        } catch (ServiceCompletionNotFoundException ignored) {}

        try {
            bo.deleteServiceCompletion(completionId2);
        } catch (ServiceCompletionNotFoundException ignored) {}

        ServiceCompletion sc1 = new ServiceCompletion(
                completionId1,
                serviceId,
                "Service completed successfully",
                LocalDateTime.now()
        );

        ServiceCompletion sc2 = new ServiceCompletion(
                completionId2,
                serviceId,
                "Post-service inspection done",
                LocalDateTime.now()
        );

        try {
            bo.addServiceCompletion(sc1);
            bo.addServiceCompletion(sc2);
        } catch (Exception e) {
            System.out.println("[ERROR] Insert failed: " + e.getMessage());
        }

        try {
            System.out.println(
                "Completion: " +
                bo.getCompletionById(completionId1).getDescription()
            );
        } catch (ServiceCompletionNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}