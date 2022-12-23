package com.idev4.setup.dto;

import java.time.Instant;

public class TypesDto {

    private Long typSeq;

    private String typId;

    private String typStr;

    private String glAcctNum;

    private Long typCtgryKey;

    private Long typStsKey;

    private String crtdBy;

    private Instant crtdDt;

    private String lastUpdBy;

    private Instant lastUpdDt;

    private Boolean delFlg;

    private Instant effStartDt;

    private Instant effEndDt;

    private Boolean crntRecFlg;

    private Long brnchSeq;

    private Long perdFlg;


    public Long getPerdFlg() {
        return brnchSeq;
    }

    public void setPerdFlg(Long perdFlg) {
        this.perdFlg = perdFlg;
    }


    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Long getTypSeq() {
        return typSeq;
    }

    public void setTypSeq(Long typSeq) {
        this.typSeq = typSeq;
    }

    public String getTypId() {
        return typId;
    }

    public void setTypId(String typId) {
        this.typId = typId;
    }

    public String getTypStr() {
        return typStr;
    }

    public void setTypStr(String typStr) {
        this.typStr = typStr;
    }

    public String getGlAcctNum() {
        return glAcctNum;
    }

    public void setGlAcctNum(String glAcctNum) {
        this.glAcctNum = glAcctNum;
    }

    public Long getTypCtgryKey() {
        return typCtgryKey;
    }

    public void setTypCtgryKey(Long typCtgryKey) {
        this.typCtgryKey = typCtgryKey;
    }

    public Long getTypStsKey() {
        return typStsKey;
    }

    public void setTypStsKey(Long typStsKey) {
        this.typStsKey = typStsKey;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }
}
