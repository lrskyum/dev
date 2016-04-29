package com.lrskyum.stocks.domain.impl.io;

import java.io.*;

/**
 *
 */
public class FileRepositoryObjectInput<T> implements RepositoryObjectInput<T> {
    private final File dataFile;
    private ObjectInput in;

    public FileRepositoryObjectInput(File dataFile) {
        this.dataFile = dataFile;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T read() throws IOException, ClassNotFoundException {
        return (T)getObjectInput().readObject();
    }

    @Override
    public void close() throws IOException {
        if (in != null) {
            in.close();
            in = null;
        }
    }

    private ObjectInput getObjectInput() throws IOException {
        if (in == null) {
            in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(dataFile)));
        }
        return in;
    }
}
