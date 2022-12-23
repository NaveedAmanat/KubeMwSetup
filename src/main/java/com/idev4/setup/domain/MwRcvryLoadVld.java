package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "MW_RCVY_LOAD_VLD")
public class MwRcvryLoadVld {

    @Id
    @Column(name = "RCVRY_LOAD_VLD_SEQ")
    private Long rcvryLoadVldSeq;

    @Column(name = "TRX_SEQ")
    private String trxSeq;

    @Column(name = "CLNT_SEQ")
    private Long clntSeq;

    @Column(name = "AMT")
    private String amt;

    @Column(name = "TRX_DATE")
    private Instant trxDate;

    @Column(name = "AGNT_ID")
    private String agntId;

    public Long getRcvryLoadVldSeq() {
        return rcvryLoadVldSeq;
    }

    public void setRcvryLoadVldSeq(Long rcvryLoadVldSeq) {
        this.rcvryLoadVldSeq = rcvryLoadVldSeq;
    }

    public String getTrxSeq() {
        return trxSeq;
    }

    public void setTrxSeq(String trxSeq) {
        this.trxSeq = trxSeq;
    }

    public Long getClntSeq() {
        return clntSeq;
    }

    public void setClntSeq(Long clntSeq) {
        this.clntSeq = clntSeq;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public Instant getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Instant trxDate) {
        this.trxDate = trxDate;
    }

    public String getAgntId() {
        return agntId;
    }

    public void setAgntId(String agntId) {
        this.agntId = agntId;
    }

}
