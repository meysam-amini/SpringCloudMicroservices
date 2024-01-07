package com.meysam.common.utils.utils;

import com.meysam.common.model.pagination.PageQueryModel;
import org.springframework.data.domain.Pageable;

public class PaginationUtils {

    public static Pageable getPageable(int inputPageNo, int inputPageSize) {
        int pageNo = inputPageNo;
        int pageSize = inputPageSize;
        PageQueryModel pageModel = new PageQueryModel();
        pageModel.setPageNumber(pageNo);
        pageModel.setPageSize(pageSize);
        Pageable pageable = pageModel.getPageable();
        return pageable;
    }

    public static Pageable getFirstPagePageable( int inputPageSize) {
        return getPageable(0, inputPageSize);
    }
}
