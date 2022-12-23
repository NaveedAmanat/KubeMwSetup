package com.idev4.setup.dto;

public class AssociateProductRelationDto {

    private Long asocPrdRelSeq;
    private Long prdSeq;
    private Long asocPrdSeq;

    public Long getAsocPrdRelSeq() {
        return asocPrdRelSeq;
    }

    public void setAsocPrdRelSeq(Long asocPrdRelSeq) {
        this.asocPrdRelSeq = asocPrdRelSeq;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public Long getAsocPrdSeq() {
        return asocPrdSeq;
    }

    public void setAsocPrdSeq(Long asocPrdSeq) {
        this.asocPrdSeq = asocPrdSeq;
    }
}
