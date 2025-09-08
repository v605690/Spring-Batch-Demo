package com.crus.Batch.Demo.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    JobOperator jobOperator;

    @GetMapping(value = "/job")
    public String testJob(@RequestParam(name = "id") String jobId) throws NoSuchJobException {

        JobParametersBuilder jobParametersBuilder =
                new JobParametersBuilder();

        if (StringUtils.hasLength(jobId)) {
            jobParametersBuilder.addString("jobId", jobId);
        }
        JobExecution jobExecution;
        try {
            jobExecution =
                    jobLauncher.run(
                            job,
                            jobParametersBuilder.toJobParameters()
                    );
        } catch (JobExecutionAlreadyRunningException
                 | JobRestartException
                 | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException e) {
            e.printStackTrace();
            // return exception message
            return e.getMessage();
        }

        try {
            Set<Long> executions = jobOperator.getRunningExecutions("employee-loader-job");
            if (!executions.isEmpty()) {
                jobOperator.stop(jobExecution.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Job started but failed to stop: " + e.getMessage();
        }
        return jobExecution.getStatus().name();
    }
}

