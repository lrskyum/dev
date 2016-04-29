package com.lrskyum.stocks.domain.impl.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 */
public class StringRepositoryObjectOutput<T> implements RepositoryObjectOutput<T> {
    private final File dataFile;
    private FileWriter fileOut;

    public StringRepositoryObjectOutput(File dataFile) {
        this.dataFile = dataFile;
    }

    @Override
    public RepositoryObjectOutput write(T o) throws IOException {
        getFileObjectOutput().write(o.toString());
        return this;
    }

    @Override
    public void close() throws IOException {
        if (fileOut != null) {
            fileOut.close();
            fileOut = null;
        }
    }

    private FileWriter getFileObjectOutput() throws IOException {
        if (fileOut == null) {
            fileOut = new FileWriter(dataFile);
        }
        return fileOut;
    }
}
