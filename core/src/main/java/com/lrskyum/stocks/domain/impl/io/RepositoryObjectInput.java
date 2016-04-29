package com.lrskyum.stocks.domain.impl.io;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 */
public interface RepositoryObjectInput<T> extends Closeable {
    T read() throws IOException, ClassNotFoundException;
}
