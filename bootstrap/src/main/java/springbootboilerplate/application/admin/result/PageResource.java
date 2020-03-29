package springbootboilerplate.application.admin.result;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class PageResource<T> {
    private long totalElements;
    private long totalPages;
    private List<T> content;

    public static PageResource toResource(Page page) {
        return PageResource.builder()
                .content(page.getContent())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
