package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "MW_REG_ACCT_SET")
public class MwRegAcctSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "REG_ACCT_SET_SEQ")
    private Long regAcctSetSeq;

    @Column(name = "bank_nm")
    private String bankName;

    @Column(name = "bank_brnch")
    private String bankBrnch;

    @Column(name = "acct_nm")
    private String AcctNm;

    @Column(name = "acct_num")
    private String AcctNum;

    @Column(name = "reg_seq")
    private Long regSeq;

    @Column(name = "crtd_by")
    private String crtdBy;

    @Column(name = "crtd_dt")
    private Instant crtdDt;

    @Column(name = "last_upd_by")
    private String lastUpdBy;

    @Column(name = "last_upd_dt")
    private Instant lastUpdDt;

    @Column(name = "del_flg")
    private Boolean delFlg;

    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "iban")
    private String iban;

    public MwRegAcctSet() {
    }

    public MwRegAcctSet(Long regAcctSetSeq, String bankName, String bankBrnch, String acctNm, String acctNum, Long regSeq, String crtdBy, Instant crtdDt, String lastUpdBy, Instant lastUpdDt, Boolean delFlg, Instant effStartDt, Instant effEndDt, Boolean crntRecFlg, String iban) {
        this.regAcctSetSeq = regAcctSetSeq;
        this.bankName = bankName;
        this.bankBrnch = bankBrnch;
        AcctNm = acctNm;
        AcctNum = acctNum;
        this.regSeq = regSeq;
        this.crtdBy = crtdBy;
        this.crtdDt = crtdDt;
        this.lastUpdBy = lastUpdBy;
        this.lastUpdDt = lastUpdDt;
        this.delFlg = delFlg;
        this.effStartDt = effStartDt;
        this.effEndDt = effEndDt;
        this.crntRecFlg = crntRecFlg;
        this.iban = iban;
    }

    public Long getRegAcctSetSeq() {
        return regAcctSetSeq;
    }

    public void setRegAcctSetSeq(Long regAcctSetSeq) {
        this.regAcctSetSeq = regAcctSetSeq;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBrnch() {
        return bankBrnch;
    }

    public void setBankBrnch(String bankBrnch) {
        this.bankBrnch = bankBrnch;
    }

    public String getAcctNm() {
        return AcctNm;
    }

    public void setAcctNm(String acctNm) {
        AcctNm = acctNm;
    }

    public String getAcctNum() {
        return AcctNum;
    }

    public void setAcctNum(String acctNum) {
        AcctNum = acctNum;
    }

    public Long getRegSeq() {
        return regSeq;
    }

    public void setRegSeq(Long regSeq) {
        this.regSeq = regSeq;
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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
