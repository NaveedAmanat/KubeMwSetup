package com.idev4.setup.dto;

public class ProductSegregateInstallmentDto {

    private Long prdSgrtInstSeq;
    private Long sgrtEntySeq;
    private String entyTypStr;
    private Long instNum;

    public Long getPrdSgrtInstSeq() {
        return prdSgrtInstSeq;
    }

    public void setPrdSgrtInstSeq(Long prdSgrtInstSeq) {
        this.prdSgrtInstSeq = prdSgrtInstSeq;
    }

    public Long getSgrtEntySeq() {
        return sgrtEntySeq;
    }

    public void setSgrtEntySeq(Long sgrtEntySeq) {
        this.sgrtEntySeq = sgrtEntySeq;
    }

    public String getEntyTypStr() {
        return entyTypStr;
    }

    public void setEntyTypStr(String entyTypStr) {
        this.entyTypStr = entyTypStr;
    }

    public Long getInstNum() {
        return instNum;
    }

    public void setInstNum(Long instNum) {
        this.instNum = instNum;
    }
}
