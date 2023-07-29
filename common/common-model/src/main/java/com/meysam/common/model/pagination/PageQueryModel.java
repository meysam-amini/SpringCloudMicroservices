package com.meysam.common.model.pagination;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageQueryModel {
    private Integer pageNumber = 1;
    private Integer pageSize = 10;
    private List<SortModel> sortModels = new ArrayList<>();


    public Pageable getPageable() {
        Sort sort = getSort();
        if (sort == null) {
            return PageRequest.of(pageNumber - 1, pageSize);
        }
        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }

    private Sort getSort() {
        List<Sort.Order> orders = null;
        setDefaultSort();
        orders = new ArrayList<>();
        for (SortModel sortModel : sortModels) {
            orders.add(new Sort.Order(sortModel.getDirection(), sortModel.getProperty()));
        }
        return Sort.by(orders);
    }

    public void setDefaultSort() {
        if (sortModels == null || sortModels.size() == 0) {
            List<String> list = new ArrayList<>();
            SortModel sortModel = new SortModel();
            sortModel.setProperty("id");
            sortModel.setDirection(Sort.Direction.DESC);
            sortModels.add(sortModel);
        }
    }
}
