package com.lrskyum.stocks.domain.impl.io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 */
public class IOFactoryImpl implements IOFactory {
    private static final File dataDir = new File("C:/Data/Stocks");

    @Override
    public <T> RepositoryObjectOutput<T> createOutputStream(String name) throws IOException {
        RepositoryObjectOutput<T> ser = new FileRepositoryObjectOutput<T>(new File(dataDir, name.toLowerCase() + ".ser"));
        RepositoryObjectOutput<T> txt = new StringRepositoryObjectOutput<T>(new File(dataDir, name.toLowerCase() + ".txt"));
        return new RepositoryObjectOutputChain<T>(Arrays.asList(ser, txt));
    }

    @Override
    public <T> RepositoryObjectInput<T> createObjectInput(String name) throws IOException {
        return new FileRepositoryObjectInput<>(new File(dataDir, name.toLowerCase() + ".ser"));
    }
}
