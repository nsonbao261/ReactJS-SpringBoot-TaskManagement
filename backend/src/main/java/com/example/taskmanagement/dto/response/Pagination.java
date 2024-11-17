package com.example.taskmanagement.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pagination<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPage;
}
