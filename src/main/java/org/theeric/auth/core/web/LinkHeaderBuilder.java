package org.theeric.auth.core.web;

import java.util.StringJoiner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.util.UriComponentsBuilder;
import org.theeric.auth.core.propertyeditor.SortEditor;

public class LinkHeaderBuilder<T> {

    private final Page<T> page;

    private final UriComponentsBuilder uriBuilder;

    private LinkHeaderBuilder(String httpUrl, Page<T> page) {
        this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
        this.page = page;
    }

    public static <T> LinkHeaderBuilder<T> of(String httpUrl, Page<T> page) {
        return new LinkHeaderBuilder<T>(httpUrl, page);
    }

    public String build() {
        final Integer size = page.getSize();
        final String sort = convert(page.getSort());

        final Pageable pageable = page.getPageable();
        final StringJoiner header = new StringJoiner(", ");

        if (page.hasNext()) {
            final Integer next = page.getNumber() + 1;
            header.add(build(next, size, sort, "next"));
        }

        if (!page.isLast()) {
            final Integer last = page.getTotalPages() - 1;
            header.add(build(last, size, sort, "last"));
        }

        if (!page.isFirst()) {
            final Integer first = pageable.first().getPageNumber();
            header.add(build(first, size, sort, "first"));
        }

        if (page.hasPrevious()) {
            final Integer prev = pageable.previousOrFirst().getPageNumber();
            header.add(build(prev, size, sort, "prev"));
        }

        return header.toString();
    };

    private String build(Integer page, Integer size, String sort, String rel) {
        uriBuilder //
                .replaceQueryParam("page", page) //
                .replaceQueryParam("size", size);

        if (StringUtils.isNotBlank(sort)) {
            uriBuilder.replaceQueryParam("sort", sort);
        }

        final String targetIRI = uriBuilder.encode().toUriString();
        return String.format("<%s>; rel=\"%s\"", targetIRI, rel);
    }

    private String convert(Sort sort) {
        final SortEditor editor = new SortEditor();
        editor.setValue(sort);
        return editor.getAsText();
    }

}
