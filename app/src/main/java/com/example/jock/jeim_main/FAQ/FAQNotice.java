package com.example.jock.jeim_main.FAQ;


public class FAQNotice {

    private String faq_title;
    private String faq_content;
    private String faq_date;
    private String faq_code;

    public FAQNotice(String faq_title, String faq_content, String faq_date, String faq_code) {
        this.faq_title = faq_title;
        this.faq_content = faq_content;
        this.faq_date = faq_date;
        this.faq_code = faq_code;
    }

    public String getFaq_title() {
        return faq_title;
    }

    public void setFaq_title(String faq_title) {
        this.faq_title = faq_title;
    }

    public String getFaq_content() {
        return faq_content;
    }

    public void setFaq_content(String faq_content) {
        this.faq_content = faq_content;
    }

    public String getFaq_date() {
        return faq_date;
    }

    public void setFaq_date(String faq_date) {
        this.faq_date = faq_date;
    }

    public String getFaq_code() {
        return faq_code;
    }

    public void setFaq_code(String faq_code) {
        this.faq_code = faq_code;
    }
}
