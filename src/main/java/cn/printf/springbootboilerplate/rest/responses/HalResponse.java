package cn.printf.springbootboilerplate.rest.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
public class HalResponse extends ResourceSupport {

    private final Map<String, Object> embedded = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("_embedded")
    public Map<String, Object> getEmbeddedResources() {
        return embedded;
    }

    public Object getEmbeddedResource(String key) {
        return embedded.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getEmbedded(String key) {
        Object maybe = getEmbeddedResource(key);
        return maybe == null ? null : (T) maybe;
    }

    public void embedResource(String relationship, ResourceSupport resource) {

        embedded.put(relationship, resource);
    }

    public void embedResource(String relationship, List<?> resource) {

        embedded.put(relationship, resource);
    }

    // for scenario event log resource
    public void embedResource(String relationship, Object object) {
        embedded.put(relationship, object);
    }
}
