package com.challet.challetservice.global.config;

import com.challet.challetservice.domain.batch.ChallengeEndTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job challengeEndJob(Step challengeEndStep){
        return new JobBuilder("challengeEndJob", jobRepository)
            .start(challengeEndStep)
            .build();
    }

    @Bean
    public Step challengeEndStep(ChallengeEndTasklet challengeEndTasklet){
        return new StepBuilder("challengeEndStep", jobRepository)
            .tasklet(challengeEndTasklet, transactionManager)
            .build();
    }

}
