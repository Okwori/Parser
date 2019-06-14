package com.ef.config;

import org.junit.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.step.NoWorkFoundStepExecutionListener;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.batch.operations.NoSuchJobException;

import static org.junit.Assert.assertEquals;

public class BatchConfigTest {

    private NoWorkFoundStepExecutionListener tested = new NoWorkFoundStepExecutionListener();

    @Test
    public void job() {
        StepExecution stepExecution = new StepExecution("AccessLog-file-load",
                new JobExecution(new JobInstance(1L, "AccessLog-Load"),
                        new JobParameters(), "AccessLog-Load"));

        stepExecution.setExitStatus(ExitStatus.COMPLETED);
        stepExecution.setReadCount(0);

        ExitStatus exitStatus = tested .afterStep(stepExecution);
        assertEquals(ExitStatus.FAILED.getExitCode(), exitStatus.getExitCode());
    }

    @Configuration
    @Import({BatchConfig.class})
    static class BatchTestConfig {

        @Autowired
        private Job job;

        @Bean
        JobLauncherTestUtils testUtils() throws NoSuchJobException {
            JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
            jobLauncherTestUtils.setJob(job);

            return jobLauncherTestUtils;
        }
    }
}