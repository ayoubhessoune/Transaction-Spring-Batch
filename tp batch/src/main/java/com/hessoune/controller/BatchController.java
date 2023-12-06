package com.hessoune.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class BatchController {

    private final Job job;
    private final JobLauncher jobLauncher;

    // Scheduled endpoint to trigger the batch job
    @Scheduled(cron = "0 0 0 1 * *")
    @GetMapping("/load/transactions")
    public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        // Create job parameters, using the current timestamp as a unique identifier
        JobParameters parameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();

        // Launch the job and get the execution status
        JobExecution jobExecution = jobLauncher.run(job, parameters);

        // Wait until the job execution is complete
        while (jobExecution.isRunning()) {
            System.out.println(".....");
        }

        // Return the final status of the job execution
        return jobExecution.getStatus();
    }
}
