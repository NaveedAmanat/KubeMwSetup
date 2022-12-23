package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "MW_brnch_remit_rel")
//@IdClass(MwBrnchRemitRelId.class)

public class MwBrnchRemitRel {

    @Id
    @Column(name = "BRNCH_REMIT_SEQ")
    private Long brnchRemitSeq;

    @Column(name = "BRNCH_SEQ")
    private Long brnchSeq;

    @Column(name = "PYMT_TYP_SEQ")
    private Long pymtTypSeq;


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

    // Added by Areeba - 27-05-2022
    @Column(name = "bank_brnch")
    private String bankBrnch;

    @Column(name = "iban")
    private String iban;

    @Column(name = "remit_flg")
    private Boolean remitFlg;
    // Ended by Areeba

    public Long getBrnchRemitSeq() {
        return brnchRemitSeq;
    }

    public void setBrnchRemitSeq(Long brnchRemitSeq) {
        this.brnchRemitSeq = brnchRemitSeq;
    }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Long getPymtTypSeq() {
        return pymtTypSeq;
    }

    public void setPymtTypSeq(Long pymtTypSeq) {
        this.pymtTypSeq = pymtTypSeq;
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

    public String getBankBrnch() {
        return bankBrnch;
    }

    public void setBankBrnch(String bankBrnch) {
        this.bankBrnch = bankBrnch;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Boolean getRemitFlg() {
        return remitFlg;
    }

    public void setRemitFlg(Boolean remitFlg) {
        this.remitFlg = remitFlg;
    }
}
