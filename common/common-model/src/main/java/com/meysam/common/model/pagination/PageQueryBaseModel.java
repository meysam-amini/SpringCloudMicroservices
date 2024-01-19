package com.meysam.common.model.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Data;

import java.util.List;

@Data
public abstract class PageQueryBaseModel {
    @JsonProperty(required = false)
    private PageQueryModel pageQueryModel = new PageQueryModel();

    @JsonIgnore
    public abstract List<BooleanExpression> getBooleanExpressions();
}
