package com.meysam.common.model.pagination;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class SortModel {
    private String property;
    private Sort.Direction direction;
}
