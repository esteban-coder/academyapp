package com.mitocode.exam.service.impl;

import com.mitocode.exam.service.ICRUD;
import com.mitocode.exam.repo.IGenericRepo;

import java.util.List;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public T create(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(T t) {
        return getRepo().save(t);
    }

    @Override
    public List<T> readAll() {
        return getRepo().findAll();
    }

    @Override
    public T readById(ID id) {
        return getRepo().findById(id).orElse(null);
    }

    @Override
    public void delete(ID id) {
        getRepo().deleteById(id);
    }
}
