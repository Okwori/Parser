package com.ef.config;

import com.ef.model.AccessLog;
import com.ef.service.AccessLogService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Value("${accesslog}")
    public String myArgs;

    @Autowired
    AccessLogService logService;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                   ItemReader<AccessLog> logItemReader, ItemWriter<AccessLog> logItemWriter){

        Step step = stepBuilderFactory.get("AccessLog-file-load")
                .<AccessLog, AccessLog>chunk(1000000)
                .reader(logItemReader)
                .writer(logItemWriter)
                .build();

        return jobBuilderFactory.get("AccessLog-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public FlatFileItemReader<AccessLog> logItemReader() throws IOException {

        FlatFileItemReader<AccessLog> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource(myArgs));
        flatFileItemReader.setName("WH-Log-Reader");
        flatFileItemReader.setLineMapper(logLineMapper());

        return flatFileItemReader;
    }

    @Bean

    public LineMapper<AccessLog> logLineMapper(){

        DefaultLineMapper<AccessLog> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter("|");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("date", "ipAddress", "request", "status", "userAgent");

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(new LogFiledMapper());

        return  defaultLineMapper;
    }
}