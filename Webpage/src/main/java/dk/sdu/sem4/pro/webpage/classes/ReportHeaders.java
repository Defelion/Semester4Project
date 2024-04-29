package dk.sdu.sem4.pro.webpage.classes;

import java.util.List;

public class ReportHeaders {
    private List<String> headings;

    public ReportHeaders() {}

    public ReportHeaders(List<String> headings) {
        this.headings = headings;
    }

    public List<String> getHeadings() {
        return headings;
    }

    public void setHeadings(List<String> headings) {
        this.headings = headings;
    }
}
