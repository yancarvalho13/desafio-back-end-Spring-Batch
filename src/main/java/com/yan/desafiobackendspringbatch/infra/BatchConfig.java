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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    private PlatformTransactionManager transactionManager;
    private JobRepository jobRepository;

    public BatchConfig(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
    }

    @Bean
    public Job job(Step step){
        return new JobBuilder("job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer()) /// Para rodar o metodo mais de uma vez
                .build();

    }

    @Bean
    public Step step(ItemReader<InputDTO> reader, ItemProcessor<InputDTO,OutputDTO> processor, ItemWriter<OutputDTO> writer) {
        return new StepBuilder("step",jobRepository)
                .<InputDTO, OutputDTO>chunk(1000,transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public FlatFileItemReader<InputDTO> reader(){
        return new FlatFileItemReaderBuilder<InputDTO>()
                .name("reader")
                .resource(new FileSystemResource("/home/yanonjava/IdeaProjects/DesafioBackEnd-SpringBatch/CNAB.txt"))
                .fixedLength()
                .columns(new Range(1,1), new Range(2,9), new Range(10,19), new Range(20,30)
                , new Range(31,42), new Range(43, 48), new Range(49,62), new Range(63,81))
                .names("tipo","data","cpf","cartao","hora","donoDaLoja","nomeDaLoja")
                .targetType(InputDTO.class)
                .build();
    }

    public ItemProcessor<InputDTO,OutputDTO> processor(){
        return item -> {
            return new OutputDTO(null,
                    item.tipo(), null,null,item.cpf(),item.cartao(),null,item.donoDaLoja().trim(),item.nomeDaLoja().trim())
                    .withData(item.data())
                    .withValorDividedByHundred(item.valor())
                    .withHora(item.hora());
        };
    }
}
