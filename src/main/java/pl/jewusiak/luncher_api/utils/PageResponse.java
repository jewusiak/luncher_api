package pl.jewusiak.luncher_api.utils;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class PageResponse<T> {
    private List<T> content;
    private int thisPage;
    private int size;
    private boolean hasNext;
    private int pages;
    private long elements;

    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponseBuilder<T>().content(page.getContent()).thisPage(page.getNumber()).size(page.getSize())
                                    .hasNext(page.hasNext()).pages(page.getTotalPages())
                                    .elements(page.getTotalElements()).build();
    }
}
