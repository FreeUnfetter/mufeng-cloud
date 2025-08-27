package com.mufeng.mufengGenerator.util;

import com.mufeng.mufengGenerator.domain.dto.PageResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageUtils {

    /**
     * 对List进行分页
     *
     * @param data     原始数据列表
     * @param pageNum  页码（从1开始）
     * @param pageSize 每页大小
     * @param <T>      数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> paginate(List<T> data, int pageNum, int pageSize) {
        if (data == null) {
            data = Collections.emptyList();
        }

        // 参数校验
        if (pageNum < 1) pageNum = 1;
        if (pageSize < 1) pageSize = 10;

        int totalCount = data.size();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 调整页码，如果超过总页数则显示最后一页
        if (pageNum > totalPages && totalPages > 0) {
            pageNum = totalPages;
        }

        // 计算起始和结束索引
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalCount);

        // 确保索引有效
        if (fromIndex >= totalCount) {
            fromIndex = 0;
            toIndex = 0;
        }

        List<T> pageData = data.subList(fromIndex, toIndex);

        return new PageResult<T>(pageNum, pageSize, totalCount, new ArrayList<>(pageData));
    }

}
