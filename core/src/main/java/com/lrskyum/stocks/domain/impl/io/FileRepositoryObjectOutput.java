package com.lrskyum.stocks.domain.impl.io;

import java.io.*;

/**
 *
 */
public class FileRepositoryObjectOutput<T> implements RepositoryObjectOutput<T> {
    private final File dataFile;
    private ObjectOutput out;

    public FileRepositoryObjectOutput(File dataFile) {
        this.dataFile = dataFile;
    }

    public File getDataFile() {
        return dataFile;
    }

    @Override
    public RepositoryObjectOutput write(T o) throws IOException {
        getObjectOutput().writeObject(o);
        return this;
    }

    @Override
    public void close() throws IOException {
        if (out != null) {
            out.close();
            out = null;
        }
    }

    private ObjectOutput getObjectOutput() throws IOException {
        if (out == null) {
            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)));
        }
        return out;
    }
}
