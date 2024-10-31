package com.meysam.backoffice.repository;

import org.hibernate.exception.DataException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseJDBCRepository<ENTITY> implements IBaseJDBCRepository<ENTITY> {

    private final DataSource dataSource;

    public BaseJDBCRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected abstract ENTITY mapResultSetToEntity(ResultSet rs) throws SQLException;

    
    @Override
    public Optional<ENTITY> findOne(String tableName, Map<String, Object> conditions) throws SQLException {
        List<Object> parameters = new ArrayList<>();
        String query = buildQueryForFindOne(tableName, conditions, parameters);
        List<ENTITY> resultList = executeQuery(query, parameters);

        if (resultList.size() == 1) {
            return Optional.of(resultList.get(0));
        } else if (resultList.size() > 1) {
            throw new RuntimeException("MORE_THAN_ONE_VALUE_FOUND");
        } else {
            return Optional.empty();
        }
    }


    @Override
    public PageResult<ENTITY> findAll(String tableName, Integer limit, Integer offset) throws SQLException {
        long totalCount = countBy(tableName, null);
        List<ENTITY> records = findBy(tableName, null, limit, offset);
        return new PageResult<>(records, totalCount, (offset != null ? offset / limit : 0) + 1, limit);
    }


    @Override
    public PageResult<ENTITY> findAllBy(String tableName, Map<String, Object> conditions, Integer limit, Integer offset) throws SQLException {
        long totalCount = countBy(tableName, conditions);
        List<ENTITY> records = findBy(tableName, conditions, limit, offset);
        return new PageResult<>(records, totalCount, (offset != null ? offset / limit : 0) + 1, limit);
    }


    @Override
    public long countBy(String tableName, Map<String, Object> conditions) throws SQLException {
        List<Object> parameters = new ArrayList<>();
        String countQuery = "SELECT COUNT(*) FROM " + tableName + buildWhere(conditions, parameters);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(countQuery)) {

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return 0;
    }


    private List<ENTITY> findBy(String tableName, Map<String, Object> conditions, Integer limit, Integer offset) throws SQLException {
        List<Object> parameters = new ArrayList<>();
        String query = buildQueryWithPagination(tableName, conditions, parameters, limit, offset);
        return executeQuery(query, parameters);
    }


    private List<ENTITY> executeQuery(String query, List<Object> parameters) throws SQLException {
        List<ENTITY> resultList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    resultList.add(mapResultSetToEntity(rs));
                }
            }
        }

        return resultList;
    }

    private String buildWhere(Map<String, Object> conditions, List<Object> parameters) {
        StringBuilder whereClause = new StringBuilder();

        if (conditions != null && !conditions.isEmpty()) {
            whereClause.append(" WHERE ");
            conditions.forEach((field, value) -> {
                whereClause.append(field).append(" = ? AND ");
                parameters.add(value);
            });
            // Remove "AND "
            whereClause.setLength(whereClause.length() - 4);
        }

        return whereClause.toString();
    }

    private String buildQueryWithPagination(String tableName, Map<String, Object> conditions, List<Object> parameters, Integer limit, Integer offset) {
        StringBuilder query = new StringBuilder("SELECT * FROM " + tableName);
        query.append(buildWhere(conditions, parameters));

        if (limit != null) {
            query.append(" LIMIT ?");
            parameters.add(limit);
        }

        if (offset != null) {
            query.append(" OFFSET ?");
            parameters.add(offset);
        }

        return query.toString();
    }

    private String buildQueryForFindOne(String tableName, Map<String, Object> conditions, List<Object> parameters) {
        StringBuilder query = new StringBuilder("SELECT * FROM " + tableName);
        query.append(buildWhere(conditions, parameters));
        return query.toString();
    }
}
