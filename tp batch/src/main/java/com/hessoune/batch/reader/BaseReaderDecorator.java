package com.hessoune.batch.reader;

import org.springframework.batch.item.*;

// An abstract base class for decorating ItemReaders
public abstract class BaseReaderDecorator<T> implements ItemStreamReader<T> {

    // The wrapped (decorated) ItemReader
    private final ItemStreamReader<T> delegate;

    // Constructor to initialize the decorator with a delegate ItemReader
    protected BaseReaderDecorator(ItemStreamReader<T> delegate) {
        this.delegate = delegate;
    }

    // Implementation of the read method, delegates to the wrapped ItemReader
    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return delegate.read();
    }

    // Implementation of the open method, delegates to the wrapped ItemReader
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.delegate.open(executionContext);
    }

    // Implementation of the update method, delegates to the wrapped ItemReader
    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        this.delegate.update(executionContext);
    }

    // Implementation of the close method, delegates to the wrapped ItemReader
    @Override
    public void close() throws ItemStreamException {
        this.delegate.close();
    }
}
