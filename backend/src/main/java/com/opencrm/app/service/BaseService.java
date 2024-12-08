package com.opencrm.app.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;

public interface BaseService<T, ID> {
    Class<T> getDomainClass();

    <S extends T> S save(S entity);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    void delete(T entity);

    void deleteById(ID id);

    void deleteAll();

    void deleteAll(Iterable<? extends T> entities);

    void deleteAllInBatch();

    <S extends T> Optional<S> findOne(Example<S> example);

    Optional<T> findById(ID id);

    List<T> findAllById(Iterable<ID> ids);

    List<T> findAll();

    List<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

    <S extends T> List<S> findAll(Example<S> example);

    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

    long count();

    <S extends T> long count(Example<S> example);

    boolean existsById(ID id);

    <S extends T> boolean exists(Example<S> example);

    void flush();

    <S extends T> S saveAndFlush(S entity);

    Page<T> findBy(Specification<T> spec, List<Sorting> sortings, OffsetPaging OffsetPaging);

    <S extends T, R> R findBy(Specification<T> spec, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
