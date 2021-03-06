package com.example.manytomany.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Transactional
public abstract class AbstractService<T extends Serializable> implements IOperations<T> {

	// read - one

	@Override
	@Transactional(readOnly = true)
	public T findOne(final long id) {
		return getJPADao().findById(id).orElse(null);
	}

	// read - all

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return Lists.newArrayList(getJPADao().findAll());
	}

	// write

	@Override
	public T create(final T entity) {
		return getJPADao().save(entity);
	}

	@Override
	public T update(final T entity) {
		return getJPADao().save(entity);
	}

	@Override
	public void delete(T entity) {
		getJPADao().delete(entity);
	}

	@Override
	public void deleteById(long entityId) {
		T entity = findOne(entityId);
		delete(entity);
	}

	protected abstract JpaRepository<T, Long> getJPADao();

}
