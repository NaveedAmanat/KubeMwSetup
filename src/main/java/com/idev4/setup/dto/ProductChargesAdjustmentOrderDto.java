package com.idev4.setup.dto;

public class ProductChargesAdjustmentOrderDto {

    private Long prdChrgAdjOrdrSeq;
    private Long adjOrdr;
    private Long prdSeq;
    private Long prdChrgSeq;

    public Long getPrdChrgAdjOrdrSeq() {
        return prdChrgAdjOrdrSeq;
    }

    public void setPrdChrgAdjOrdrSeq(Long prdChrgAdjOrdrSeq) {
        this.prdChrgAdjOrdrSeq = prdChrgAdjOrdrSeq;
    }

    public Long getAdjOrdr() {
        return adjOrdr;
    }

    public void setAdjOrdr(Long adjOrdr) {
        this.adjOrdr = adjOrdr;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public Long getPrdChrgSeq() {
        return prdChrgSeq;
    }

    public void setPrdChrgSeq(Long prdChrgSeq) {
        this.prdChrgSeq = prdChrgSeq;
    }
}
