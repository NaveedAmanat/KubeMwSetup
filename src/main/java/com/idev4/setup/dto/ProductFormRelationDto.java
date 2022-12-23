package com.idev4.setup.dto;

public class ProductFormRelationDto {

    private Long prdFormRelSeq;

    private Long formSeq;

    private Long prdSeq;

    private Long formSortOrdr;

    public Long getPrdFormRelSeq() {
        return prdFormRelSeq;
    }

    public void setPrdFormRelSeq(Long prdFormRelSeq) {
        this.prdFormRelSeq = prdFormRelSeq;
    }

    public Long getFormSeq() {
        return formSeq;
    }

    public void setFormSeq(Long formSeq) {
        this.formSeq = formSeq;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public Long getFormSortOrdr() {
        return formSortOrdr;
    }

    public void setFormSortOrdr(Long formSortOrdr) {
        this.formSortOrdr = formSortOrdr;
    }

}
