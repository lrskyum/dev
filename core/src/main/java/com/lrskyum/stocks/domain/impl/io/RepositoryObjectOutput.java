package com.lrskyum.stocks.domain.impl.io;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 */
public interface RepositoryObjectOutput<T> extends Closeable {
    RepositoryObjectOutput write(T o) throws IOException;
}
