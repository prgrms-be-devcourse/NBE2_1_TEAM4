package com.example.gccoffee.dto.page;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequestDTO {
    @Builder.Default
    @Min(1)
    private int page = 1;

    @Builder.Default
    @Min(5)
    @Max(100)
    private int size = 5;

    public Pageable getPageable(Sort sort) {
        int pageNum = page < 0 ? 1 : page - 1;
        int sizeNum = size < 5 ? 5 : size;

        return PageRequest.of(pageNum, sizeNum, sort);
    }
}
