package com.idev4.setup.dto;

public class ProductAdvanceRuleDto {

    private Long prdAdvRulSeq;
    private Long rulSeq;
    private Long prdSeq;

    private String rulId;
    private Long rulCtgryKey;
    private String rulNm;
    private String rulCmnt;
    private String rulCrtraStr;

    public Long getPrdAdvRulSeq() {
        return prdAdvRulSeq;
    }

    public void setPrdAdvRulSeq(Long prdAdvRulSeq) {
        this.prdAdvRulSeq = prdAdvRulSeq;
    }

    public Long getRulSeq() {
        return rulSeq;
    }

    public void setRulSeq(Long rulSeq) {
        this.rulSeq = rulSeq;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public String getRulId() {
        return rulId;
    }

    public void setRulId(String rulId) {
        this.rulId = rulId;
    }

    public Long getRulCtgryKey() {
        return rulCtgryKey;
    }

    public void setRulCtgryKey(Long rulCtgryKey) {
        this.rulCtgryKey = rulCtgryKey;
    }

    public String getRulNm() {
        return rulNm;
    }

    public void setRulNm(String rulNm) {
        this.rulNm = rulNm;
    }

    public String getRulCmnt() {
        return rulCmnt;
    }

    public void setRulCmnt(String rulCmnt) {
        this.rulCmnt = rulCmnt;
    }

    public String getRulCrtraStr() {
        return rulCrtraStr;
    }

    public void setRulCrtraStr(String rulCrtraStr) {
        this.rulCrtraStr = rulCrtraStr;
    }

}
