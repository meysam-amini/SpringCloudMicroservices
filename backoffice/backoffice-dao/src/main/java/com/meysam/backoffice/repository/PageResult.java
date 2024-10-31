package com.meysam.backoffice.repository;

import lombok.*;

import java.util.List;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor@Builder
public class PageResult<T> {
    private List<T> records;
    private long totalCount;
    private int pageNumber;
    private int pageSize;
}
