package com.mufeng.mufengGenerator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResult<T> {
    private int pageNum;
    private int pageSize;
    private long totalCount;
    private List<T> data;

    @Override
    public String toString() {
        return "PageResult{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", data=" + data +
                '}';
    }
}
