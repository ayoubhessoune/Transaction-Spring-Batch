package com.hessoune.batch.config;

import com.hessoune.batch.processor.CompteProcessor;
import com.hessoune.batch.processor.TransactionProcessor;
import com.hessoune.batch.reader.LoggingTransactionReader;
import com.hessoune.batch.reader.TransactionCsvReader;
import com.hessoune.batch.reader.TransactionXmlReader;
import com.hessoune.batch.writer.TransactionWriter;
import com.hessoune.dto.TransactionDto;
import com.hessoune.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

    private final TransactionProcessor transactionProcessor;
    private final CompteProcessor compteProcessor;
    private final TransactionWriter transactionWriter;

    // Job configuration for importing transactions
    @Bean
    public Job importTransactions(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("importTransactions", jobRepository)
                .start(splitFlow(jobRepository, transactionManager))
                .build().build();
    }

    // Flow configuration to split processing between CSV and XML data
    @Bean
    public Flow splitFlow(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(new SimpleAsyncTaskExecutor())
                .add(
                        csvFlow(jobRepository, transactionManager),
                        xmlFlow(jobRepository, transactionManager))
                .build();
    }

    // Flow configuration for processing CSV data
    @Bean
    public Flow csvFlow(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new FlowBuilder<SimpleFlow>("csvFlow")
                .start(csvStep(jobRepository, transactionManager))
                .build();
    }

    // Flow configuration for processing XML data
    @Bean
    public Flow xmlFlow(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new FlowBuilder<SimpleFlow>("xmlFlow")
                .start(xmlStep(jobRepository, transactionManager))
                .build();
    }

    // Step configuration for processing CSV data
    @Bean
    public Step csvStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("csv-import-transactions-step", jobRepository)
                .<TransactionDto, Transaction>chunk(10, transactionManager)
                .reader(new LoggingTransactionReader(transactionCsvReader()))
                .processor(compositeItemProcessor())
                .writer(transactionWriter)
                .build();
    }

    // Step configuration for processing XML data
    @Bean
    public Step xmlStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("xml-import-transactions-step", jobRepository)
                .<TransactionDto, Transaction>chunk(10, transactionManager)
                .reader(new LoggingTransactionReader(transactionXmlReader()))
                .processor(compositeItemProcessor())
                .writer(transactionWriter)
                .build();
    }

    // Configuration for a composite item processor that applies both transaction and compte processors
    @Bean
    public CompositeItemProcessor compositeItemProcessor() {
        List<ItemProcessor> delegates = new ArrayList<>();
        delegates.add(transactionProcessor);
        delegates.add(compteProcessor);

        CompositeItemProcessor processor = new CompositeItemProcessor();
        processor.setDelegates(delegates);

        return processor;
    }

    // Configuration for the CSV file reader
    @Bean
    public FlatFileItemReader transactionCsvReader() {
        return new TransactionCsvReader();
    }

    // Configuration for the XML file reader
    @Bean
    public StaxEventItemReader transactionXmlReader() {
        return new TransactionXmlReader();
    }
}
