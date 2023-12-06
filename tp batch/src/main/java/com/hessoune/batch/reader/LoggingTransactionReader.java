package com.hessoune.batch.reader;

import com.hessoune.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.*;

// A decorator for logging transactions read by the underlying ItemReader
public class LoggingTransactionReader extends BaseReaderDecorator<TransactionDto> {

    // Logger for logging information
    private static final Logger logger = LoggerFactory.getLogger(LoggingTransactionReader.class);

    // Constructor to initialize the decorator with a delegate ItemReader
    public LoggingTransactionReader(ItemStreamReader<TransactionDto> delegate) {
        super(delegate);
    }

    // Overrides the read method to add logging functionality
    @Override
    public TransactionDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // Call the read method of the wrapped ItemReader
        TransactionDto transactionDto = super.read();

        // If the read result is not null, log information about the transaction
        if (transactionDto != null) {
            logger.info("Read Transaction with id = " + transactionDto.idTransaction());
        }

        // Return the read result (TransactionDto)
        return transactionDto;
    }
}
