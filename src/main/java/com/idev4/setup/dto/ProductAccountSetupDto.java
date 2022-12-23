package com.idev4.setup.dto;

public class ProductAccountSetupDto {

    private Long prdAcctSetSeq;
    private Long acctCtgryKey;
    private String glAcctNum;
    private Long prdSeq;

    public Long getPrdAcctSetSeq() {
        return prdAcctSetSeq;
    }

    public void setPrdAcctSetSeq(Long prdAcctSetSeq) {
        this.prdAcctSetSeq = prdAcctSetSeq;
    }

    public Long getAcctCtgryKey() {
        return acctCtgryKey;
    }

    public void setAcctCtgryKey(Long acctCtgryKey) {
        this.acctCtgryKey = acctCtgryKey;
    }

    public String getGlAcctNum() {
        return glAcctNum;
    }

    public void setGlAcctNum(String glAcctNum) {
        this.glAcctNum = glAcctNum;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }
}
