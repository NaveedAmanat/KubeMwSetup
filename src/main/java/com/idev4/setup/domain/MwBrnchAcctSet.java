package com.idev4.setup.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwBrnchAcctSet.
 */
@Entity
@Table(name = "mw_brnch_acct_set")
//@IdClass(MwBrnchAcctSetId.class)
public class MwBrnchAcctSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "brnch_acct_set_seq")
    private Long brnchAcctSetSeq;

    @Column(name = "bank_nm")
    private String bankName;

    @Column(name = "bank_brnch")
    private String bankBrnch;

    @Column(name = "acct_nm")
    private String AcctNm;

    @Column(name = "acct_num")
    private String AcctNum;

    @Column(name = "brnch_seq")
    private Long brnchSeq;

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

    //    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "iban")
    private String iban;

    // Added by Areeba - 27-05-2022
    @Column(name = "onln_flg")
    private Boolean onlnFlg;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "ibft_bank_code")
    private String ibftBankCode;
    // Ended by Areeba

    public Long getBrnchAcctSetSeq() {
        return brnchAcctSetSeq;
    }

    public void setBrnchAcctSetSeq(Long brnchAcctSetSeq) {
        this.brnchAcctSetSeq = brnchAcctSetSeq;
    }

    public MwBrnchAcctSet brnchAcctSetSeq(Long brnchAcctSetSeq) {
        this.brnchAcctSetSeq = brnchAcctSetSeq;
        return this;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public MwBrnchAcctSet bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public String getBankBrnch() {
        return bankBrnch;
    }

    public void setBankBrnch(String bankBrnch) {
        this.bankBrnch = bankBrnch;
    }

    public MwBrnchAcctSet bankBrnch(String bankBrnch) {
        this.bankBrnch = bankBrnch;
        return this;
    }

    public String getAcctNm() {
        return AcctNm;
    }

    public void setAcctNm(String AcctNm) {
        this.AcctNm = AcctNm;
    }

    public MwBrnchAcctSet AcctNm(String AcctNm) {
        this.AcctNm = AcctNm;
        return this;
    }

    public String getAcctNum() {
        return AcctNum;
    }

    public void setAcctNum(String AcctNum) {
        this.AcctNum = AcctNum;
    }

    public MwBrnchAcctSet AcctNum(String AcctNum) {
        this.AcctNum = AcctNum;
        return this;
    }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public MwBrnchAcctSet brnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwBrnchAcctSet crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwBrnchAcctSet crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwBrnchAcctSet lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwBrnchAcctSet lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwBrnchAcctSet delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwBrnchAcctSet effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwBrnchAcctSet effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwBrnchAcctSet crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public MwBrnchAcctSet iban(String iban) {
        this.iban = iban;
        return this;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Boolean getOnlnFlg() {
        return onlnFlg;
    }

    public void setOnlnFlg(Boolean onlnFlg) {
        this.onlnFlg = onlnFlg;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getIbftBankCode() {
        return ibftBankCode;
    }

    public void setIbftBankCode(String ibftBankCode) {
        this.ibftBankCode = ibftBankCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwBrnchAcctSet mwBrnchAcctSet = (MwBrnchAcctSet) o;
        if (mwBrnchAcctSet.getBrnchAcctSetSeq() == null || getBrnchAcctSetSeq() == null) {
            return false;
        }
        return Objects.equals(getBrnchAcctSetSeq(), mwBrnchAcctSet.getBrnchAcctSetSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBrnchAcctSetSeq());
    }

    @Override
    public String toString() {
        return "MwBrnchAcctSet{" +
            "id=" + getBrnchAcctSetSeq() +
            ", brnchAcctSetSeq=" + getBrnchAcctSetSeq() +
            ", bankName='" + getBankName() + "'" +
            ", bankBrnch='" + getBankBrnch() + "'" +
            ", AcctNm='" + getAcctNm() + "'" +
            ", AcctNum='" + getAcctNum() + "'" +
            ", brnchSeq=" + getBrnchSeq() +
            ", crtdBy='" + getCrtdBy() + "'" +
            ", crtdDt='" + getCrtdDt() + "'" +
            ", lastUpdBy='" + getLastUpdBy() + "'" +
            ", lastUpdDt='" + getLastUpdDt() + "'" +
            ", delFlg='" + isDelFlg() + "'" +
            ", effStartDt='" + getEffStartDt() + "'" +
            ", effEndDt='" + getEffEndDt() + "'" +
            ", crntRecFlg='" + isCrntRecFlg() + "'" +
            ", iban='" + getIban() + "'" +
            "}";
    }
}
