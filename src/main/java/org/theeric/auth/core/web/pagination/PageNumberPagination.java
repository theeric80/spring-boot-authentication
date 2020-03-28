package org.theeric.auth.core.web.pagination;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class PageNumberPagination<T> {

    protected String pageQueryParam = "page";
    protected String sizeQueryParam = "size";
    protected String sortQueryParam = "sort";

    protected final Page<T> page;
    protected final HttpServletRequest request;
    protected final UriComponentsBuilder uriBuilder;

    private PageNumberPagination(Page<T> page, HttpServletRequest request) {
        final String httpUrl = request.getRequestURL().toString();

        this.page = page;
        this.request = request;
        this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
    }

    public static <T> PageNumberPagination<T> of(Page<T> page, HttpServletRequest request) {
        return new PageNumberPagination<T>(page, request);
    }

    public PageNumberPagination<T> pageQueryParam(String pageQueryParam) {
        this.pageQueryParam = pageQueryParam;
        return this;
    }

    public PageNumberPagination<T> sizeQueryParam(String sizeQueryParam) {
        this.sizeQueryParam = sizeQueryParam;
        return this;
    }

    public PageNumberPagination<T> sortQueryParam(String sortQueryParam) {
        this.sortQueryParam = sortQueryParam;
        return this;
    }

    public <E> ResponseEntity<List<E>> build(List<E> data) {
        return ResponseEntity.ok() //
                .header(HttpHeaders.LINK, buildLinkHeader()) //
                .body(data);
    }

    private String buildLinkHeader() {
        final Integer size = page.getSize();
        final String sort = getSort().orElse("");

        final Pageable pageable = page.getPageable();
        final StringJoiner header = new StringJoiner(", ");

        if (page.hasNext()) {
            final Integer next = page.getNumber() + 1;
            header.add(buildLink(next, size, sort, "next"));
        }

        if (!page.isLast()) {
            final Integer last = page.getTotalPages() - 1;
            header.add(buildLink(last, size, sort, "last"));
        }

        if (!page.isFirst()) {
            final Integer first = pageable.first().getPageNumber();
            header.add(buildLink(first, size, sort, "first"));
        }

        if (page.hasPrevious()) {
            final Integer prev = pageable.previousOrFirst().getPageNumber();
            header.add(buildLink(prev, size, sort, "prev"));
        }

        return header.toString();
    };

    private String buildLink(Integer page, Integer size, String sort, String rel) {
        uriBuilder //
                .replaceQueryParam(pageQueryParam, page) //
                .replaceQueryParam(sizeQueryParam, size);

        if (StringUtils.isNotBlank(sort)) {
            uriBuilder.replaceQueryParam(sortQueryParam, sort);
        }

        final String targetIRI = uriBuilder.encode().toUriString();
        return String.format("<%s>; rel=\"%s\"", targetIRI, rel);
    }

    private Optional<String> getSort() {
        return Optional.ofNullable(request.getParameter(sortQueryParam));
    }

}
