package com.challet.challetservice.domain.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeEndScheduler {

    private final JobLauncher jobLauncher;
    private final Job challengeEndJob;

    @Scheduled(cron = "0 8 22 * * ?")
    public void challengeEndJob()
        throws Exception {
        jobLauncher.run(challengeEndJob, new JobParametersBuilder()
            .addLong("time",System.currentTimeMillis())
            .toJobParameters());
    }

}
