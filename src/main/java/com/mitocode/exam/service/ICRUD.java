package com.mitocode.exam.service;

import java.util.List;

public interface ICRUD<T, ID> {

    T create(T t);

    T update(T t);

    List<T> readAll();

    T readById(ID id);

    void delete(ID id);
}
