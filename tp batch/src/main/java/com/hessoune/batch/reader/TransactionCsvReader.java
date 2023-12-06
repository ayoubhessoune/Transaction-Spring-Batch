package com.hessoune.batch.reader;

import com.hessoune.dto.TransactionDto;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

// Custom ItemReader for reading TransactionDto from a CSV file
public class TransactionCsvReader extends FlatFileItemReader<TransactionDto> {

    // Constructor to configure the CSV reader properties
    public TransactionCsvReader() {
        this.setResource(new ClassPathResource("data.csv")); // Set the resource (CSV file)
        this.setName("csvReader");
        this.setLinesToSkip(1); // Skip the header line in the CSV file
        this.setLineMapper(lineMapper()); // Set the line mapper for mapping lines to TransactionDto
    }

    // Configures the line mapper for mapping lines to TransactionDto
    private LineMapper<TransactionDto> lineMapper() {
        DefaultLineMapper<TransactionDto> defaultLineMapper = new DefaultLineMapper<>();

        // Configure the line tokenizer with comma as the delimiter
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("idTransaction", "idCompte", "montant", "dateTransaction");

        defaultLineMapper.setLineTokenizer(lineTokenizer);

        // Configure the field set mapper to map fields to TransactionDto
        defaultLineMapper.setFieldSetMapper(fieldSet -> {
            Long idTransaction = fieldSet.readLong("idTransaction");
            Long idCompte = fieldSet.readLong("idCompte");
            double montant = fieldSet.readDouble("montant");
            String dateTransaction = fieldSet.readString("dateTransaction");

            return new TransactionDto(idTransaction, idCompte, montant, dateTransaction);
        });

        return defaultLineMapper;
    }
}
