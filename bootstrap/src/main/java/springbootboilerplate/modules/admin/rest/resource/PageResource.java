package springbootboilerplate.modules.admin.rest.resource;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class PageResource<T> {
    private long totalElements;
    private List<T> content;

    public static PageResource toResource(Page page) {
        return PageResource.builder()
                .content(page.getContent())
                .totalElements(page.getTotalElements()).build();
    }
}
