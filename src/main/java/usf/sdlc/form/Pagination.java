package usf.sdlc.form;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class Pagination {
    public Pagination() {
    }

    public Pagination(int page, int max) {
        this.page = page;
        this.max = max;
    }

    private int page;
    private int max;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
