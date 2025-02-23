package com.yan.desafiobackendspringbatch.infra;


import com.yan.desafiobackendspringbatch.Dto.InputDTO;
import com.yan.desafiobackendspringbatch.Dto.OutputDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    private PlatformTransactionManager transactionManager;
    private JobRepository jobRepository;

    public BatchConfig(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
    }

    public Job job(Step step){
        return new JobBuilder("job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer()) /// Para rodar o metodo mais de uma vez
                .build();

    }

    public Step step(ItemReader<InputDTO> reader, ItemProcessor<InputDTO,OutputDTO> processor, ItemWriter<OutputDTO> writer) {
        return new StepBuilder("step",jobRepository)
                .<InputDTO, OutputDTO>chunk(1000,transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
