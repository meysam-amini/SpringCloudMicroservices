package com.meysam.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.math.BigInteger;

@NoRepositoryBean
public interface BaseRepository<ENTITY_TYPE> extends JpaRepository<ENTITY_TYPE, BigInteger>, CrudRepository<ENTITY_TYPE, BigInteger>, QuerydslPredicateExecutor<ENTITY_TYPE> {
}
