package com.lrskyum.stocks.domain.impl.io;

import java.io.IOException;

/**
 *
 */
public interface IOFactory {
    <T> RepositoryObjectOutput<T> createOutputStream(String name) throws IOException;

    <T> RepositoryObjectInput<T> createObjectInput(String name) throws IOException;
}
