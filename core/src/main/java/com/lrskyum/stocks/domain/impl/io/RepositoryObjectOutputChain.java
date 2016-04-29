package com.lrskyum.stocks.domain.impl.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RepositoryObjectOutputChain<T> implements RepositoryObjectOutput<T> {
    private final List<RepositoryObjectOutput<T>> chain;

    public RepositoryObjectOutputChain() {
        chain = new ArrayList<>();
    }

    public RepositoryObjectOutputChain(List<RepositoryObjectOutput<T>> chain) {
        this.chain = new ArrayList<>(chain);
    }

    public RepositoryObjectOutputChain<T> add(RepositoryObjectOutput<T> e) {
        chain.add(e);
        return this;
    }

    @Override
    public RepositoryObjectOutput write(T obj) throws IOException {
        for (RepositoryObjectOutput<T> o : chain) {
            o.write(obj);
        }
        return this;
    }

    @Override
    public void close() throws IOException {
        for (RepositoryObjectOutput<T> o : chain) {
            o.close();
        }
    }
}
