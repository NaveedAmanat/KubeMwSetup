package com.idev4.setup.dto;

import java.util.List;

public class ProductChargesDto {

    public List<PrdChrgSlbDto> slbs;
    private Long prdChrgSeq;
    private Long prdSeq;
    private Long rulSeq;
    private Long chrgCalcTypKey;
    private Double chrgVal;
    private Boolean upfrontFlg;
    private Long sgrtInstNum;
    private Boolean adjustRoundingFlg;
    private String chargeName;
    private Long chrgTypSeq;
    private long typSeq;

    public Long getChrgTypSeq() {
        return chrgTypSeq;
    }

    public void setChrgTypSeq(Long chrgTypSeq) {
        this.chrgTypSeq = chrgTypSeq;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public long getTypSeq() {
        return typSeq;
    }

    public void setTypSeq(long typSeq) {
        this.typSeq = typSeq;
    }

    public Long getPrdChrgSeq() {
        return prdChrgSeq;
    }

    public void setPrdChrgSeq(Long prdChrgSeq) {
        this.prdChrgSeq = prdChrgSeq;
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

    public Long getChrgCalcTypKey() {
        return chrgCalcTypKey;
    }

    public void setChrgCalcTypKey(Long chrgCalcTypKey) {
        this.chrgCalcTypKey = chrgCalcTypKey;
    }

    public Double getChrgVal() {
        return chrgVal;
    }

    public void setChrgVal(Double chrgVal) {
        this.chrgVal = chrgVal;
    }

    public Boolean getUpfrontFlg() {
        return upfrontFlg;
    }

    public void setUpfrontFlg(Boolean upfrontFlg) {
        this.upfrontFlg = upfrontFlg;
    }

    public Long getSgrtInstNum() {
        return sgrtInstNum;
    }

    public void setSgrtInstNum(Long sgrtInstNum) {
        this.sgrtInstNum = sgrtInstNum;
    }

    public Boolean getAdjustRoundingFlg() {
        return adjustRoundingFlg;
    }

    public void setAdjustRoundingFlg(Boolean adjustRoundingFlg) {
        this.adjustRoundingFlg = adjustRoundingFlg;
    }

}
