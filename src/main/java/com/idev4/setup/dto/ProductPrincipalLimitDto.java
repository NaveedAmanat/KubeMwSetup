package com.idev4.setup.dto;

public class ProductPrincipalLimitDto {

    private Long prdPpalLmtSeq;
    private Double minAmt;
    private Double maxAmt;
    private Long prdSeq;
    private Long rulSeq;
    private Long sgrtInstNum;

    public Long getSgrtInstNum() {
        return sgrtInstNum;
    }

    public void setSgrtInstNum(Long sgrtInstNum) {
        this.sgrtInstNum = sgrtInstNum;
    }

    public Long getPrdPpalLmtSeq() {
        return prdPpalLmtSeq;
    }

    public void setPrdPpalLmtSeq(Long prdPpalLmtSeq) {
        this.prdPpalLmtSeq = prdPpalLmtSeq;
    }

    public Double getMinAmt() {
        return minAmt;
    }

    public void setMinAmt(Double minAmt) {
        this.minAmt = minAmt;
    }

    public Double getMaxAmt() {
        return maxAmt;
    }

    public void setMaxAmt(Double maxAmt) {
        this.maxAmt = maxAmt;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public Long getRulSeq() {
        return rulSeq;
    }

    public void setRulSeq(Long rulSeq) {
        this.rulSeq = rulSeq;
    }

}
