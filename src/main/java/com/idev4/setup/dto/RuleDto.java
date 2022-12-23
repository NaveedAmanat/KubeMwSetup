package com.idev4.setup.dto;

public class RuleDto {

    private Long rulSeq;
    private String rulId;
    private Long rulCtgryKey;
    private String rulNm;
    private String rulCmnt;
    private String rulCrtraStr;

    public Long getRulSeq() {
        return rulSeq;
    }

    public void setRulSeq(Long rulSeq) {
        this.rulSeq = rulSeq;
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
