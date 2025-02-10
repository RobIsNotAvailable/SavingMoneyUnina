package com.smu.dao;

import java.util.List;

public interface DAO<T,K> 
{
    T get(K UniqueKey);

    List<T> getAll();
}