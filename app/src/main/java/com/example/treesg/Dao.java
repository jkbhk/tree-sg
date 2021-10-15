package com.example.treesg;

import java.util.List;

public interface Dao <T>{

    public void create(T t);
    public List<T> read(T t);
    public void update(T t);
    public void delete(T t);

}
