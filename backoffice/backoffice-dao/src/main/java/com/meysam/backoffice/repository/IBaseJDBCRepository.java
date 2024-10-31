package com.meysam.backoffice.repository;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public interface IBaseJDBCRepository<ENTITY>{

    Optional<ENTITY> findOne(String tableName, Map<String, Object> conditions) throws SQLException;

    PageResult<ENTITY> findAll(String tableName, Integer limit, Integer offset) throws SQLException;

    PageResult<ENTITY> findAllBy(String tableName, Map<String, Object> conditions, Integer limit, Integer offset) throws SQLException;

    long countBy(String tableName, Map<String, Object> conditions) throws SQLException;
}
