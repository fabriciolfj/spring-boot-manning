package com.manning.sbip.ch01.springbootappdemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends CrudRepository<T, ID> {

    <S extends T> S save(S entity);

    Iterable<T> findAll();
}
