package com.example.treesg;

import java.util.List;
import java.util.function.Consumer;

public interface Dao <T>{

    public void create(T t);
    public void read(String identifier, Consumer<T> callback);
    public void readAll(String collectionID, Consumer<List<T>> callback);
    public void update(T t);
    public void delete(String identifier, Runnable callback);

}
