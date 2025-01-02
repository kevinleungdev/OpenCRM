package com.opencrm.app.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.FluentQuery;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.utils.GenericsUtils;

public class BaseServiceImpl<T, ID, R extends JpaRepository<T, ID>> implements BaseService<T, ID> {
    @SuppressWarnings("rawtypes")
    private static final Map<Class, Class> DOMAIN_CLASS_CACHE = new ConcurrentHashMap<>();

    private final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    protected R getRepository() {
        return repository;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<T> getDomainClass() {
        Class<?> thisClass = getClass();

        Class<T> domainClass = DOMAIN_CLASS_CACHE.get(thisClass);
        if (Objects.isNull(domainClass)) {
            domainClass = GenericsUtils.getGenericClass(thisClass, 0);
            DOMAIN_CLASS_CACHE.putIfAbsent(thisClass, domainClass);
        }

        return domainClass;
    }

    @Override
    public <S extends T> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        repository.deleteAll(entities);
    }

    @Override
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return repository.findOne(example);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return repository.count(example);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return repository.exists(example);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return repository.saveAndFlush(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<T> findBy(Specification<T> spec, List<Sorting> sortings,
            OffsetPaging offsetPaging) {
        if (JpaSpecificationExecutor.class.isAssignableFrom(repository.getClass())) {
            JpaSpecificationExecutor<T> executor = (JpaSpecificationExecutor<T>) repository;

            if (offsetPaging == null) {
                return executor.findBy(spec, query -> query.page(PageRequest.of(0, Integer.MAX_VALUE)));
            } else {
                return executor.findBy(spec, query -> query.page(offsetPaging.toPageable(sortings)));
            }
        } else {
            throw new UnsupportedOperationException(
                    "Unimplemented method 'findBy(Specification<T> spec, List<Sorting> sortings, OffsetPaging paging)'");
        }
    }

    @SuppressWarnings({ "hiding", "unchecked" })
    @Override
    public <S extends T, R> R findBy(Specification<T> spec, Function<FluentQuery.FetchableFluentQuery<S>, R> query) {
        if (JpaSpecificationExecutor.class.isAssignableFrom(repository.getClass())) {
            JpaSpecificationExecutor<T> executor = (JpaSpecificationExecutor<T>) repository;
            return executor.findBy(spec, query);
        } else {
            throw new UnsupportedOperationException(
                    "Unimplemented method 'findBy(Specification<T> spec, Function<FluentQuery.FetchableFluentQuery<S>, R> query)'");
        }
    }
}
