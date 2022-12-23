package com.idev4.setup.dto;


public class ProductLoanTermsDto {

    private Long prdTrmSeq;
    private Long trmKey;
    private Long pymtFreqKey;
    private Long prdSeq;
    private Long rulSeq;

    public Long getPrdTrmSeq() {
        return prdTrmSeq;
    }

    public void setPrdTrmSeq(Long prdTrmSeq) {
        this.prdTrmSeq = prdTrmSeq;
    }

    public Long getTrmKey() {
        return trmKey;
    }

    public void setTrmKey(Long trmKey) {
        this.trmKey = trmKey;
    }

    public Long getPymtFreqKey() {
        return pymtFreqKey;
    }

    public void setPymtFreqKey(Long pymtFreqKey) {
        this.pymtFreqKey = pymtFreqKey;
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
