package com.meysam.common.model.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Data
public class PageQueryModel {
    private Integer pageNumber = 0;
    private Integer pageSize = 10;
    private List<SortModel> sortModels = new ArrayList<>();
    private List<Sort.Direction> direction;

    @JsonIgnore
    public Pageable getPageable() {
        Sort sort = getSort();
        if (sort == null) {
            return PageRequest.of(pageNumber, pageSize);
        }
        return PageRequest.of(pageNumber , pageSize, sort);
    }

    @JsonIgnore
    public static Pageable getPageable(Integer pageNumber, Integer pageSize, Order direction, String sortBy) {
        Sort sort;
        if (Objects.isNull(pageNumber))
            pageNumber=0;
        if (Objects.isNull(pageSize))
            pageSize=10;
        if (Objects.nonNull(sortBy)) {
            if (Objects.nonNull(direction))
                sort = getSort(sortBy, direction==Order.ASC? Sort.Direction.ASC:Sort.Direction.DESC);
            else
                sort = getSort(sortBy, Sort.Direction.ASC);
        } else {
            if (Objects.nonNull(direction))
                sort = getSort("id", direction==Order.ASC? Sort.Direction.ASC:Sort.Direction.DESC);
            else sort = getSort("id", Sort.Direction.ASC);
        }

        return PageRequest.of(pageNumber, pageSize, sort);
    }

    @JsonIgnore
    public static OrderSpecifier<?> getSortedColumn(EntityPathBase<?> table, Order order, String fieldName) {
        Path<Object> fieldPath = Expressions.path(Object.class, table, fieldName);
        return new OrderSpecifier(order, fieldPath);
    }

    @JsonIgnore
    private static Sort getSort(String sortBy, Sort.Direction direction) {
        List<Sort.Order> orders = null;
        orders = new ArrayList<>();
        orders.add(new Sort.Order(direction,sortBy));
        return Sort.by(orders);
    }

    @JsonIgnore
    private Sort getSort() {
        List<Sort.Order> orders = null;
        setDefaultSort();
        orders = new ArrayList<>();
        for (SortModel sortModel : sortModels) {
            orders.add(new Sort.Order(sortModel.getDirection(), sortModel.getProperty()));
        }
        return Sort.by(orders);
    }

    @JsonIgnore
    public void setDefaultSort() {
        if (sortModels == null || sortModels.size() == 0) {
            List<String> list = new ArrayList<>();
            SortModel sortModel = new SortModel();
            sortModel.setProperty("id");
            sortModel.setDirection(Sort.Direction.DESC);
            sortModels.add(sortModel);
        }
    }

    @JsonIgnore
    public void setSort(String sort) {
        if (sortModels == null || sortModels.size() == 0) {
            List<String> list = new ArrayList<>();
            SortModel sortModel = new SortModel();
            sortModel.setProperty(sort);
            sortModel.setDirection(Sort.Direction.DESC);
            sortModels.add(sortModel);
        }
    }
}