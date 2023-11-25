package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageInfoResponse {
    private int totalPages;
    private long totalSize;
    private int pageNumber;
    private int pageSize;
}
