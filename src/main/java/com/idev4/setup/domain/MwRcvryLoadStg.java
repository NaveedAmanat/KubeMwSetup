package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "MW_rcvry_Load_stg")
public class MwRcvryLoadStg {

    @Id
    @Column(name = "rcvry_Load_stg_seq")
    private Long rcvryLoadStgSeq;

    @Column(name = "clnt_Id")
    private String clntId;

    @Column(name = "trx_id")
    private String trxId;

    @Column(name = "trx_dt")
    private Instant trxDt;

    @Column(name = "Amt")
    private Double amt;

    @Column(name = "trx_sts_Key")

    private Boolean trxStsKey;

    @Column(name = "Agent_id")
    private Long agentId;

    @Column(name = "Load_dt")
    private Instant loadDt;

    @Column(name = "cmnt")
    private String cmnt;

    public Long getRcvryLoadStgSeq() {
        return rcvryLoadStgSeq;
    }

    public void setRcvryLoadStgSeq(Long rcvryLoadStgSeq) {
        this.rcvryLoadStgSeq = rcvryLoadStgSeq;
    }

    public String getClntId() {
        return clntId;
    }

    public void setClntId(String clntId) {
        this.clntId = clntId;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public Instant getTrxDt() {
        return trxDt;
    }

    public void setTrxDt(Instant trxDt) {
        this.trxDt = trxDt;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Boolean getTrxStsKey() {
        return trxStsKey;
    }

    public void setTrxStsKey(Boolean trxStsKey) {
        this.trxStsKey = trxStsKey;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Instant getLoadDt() {
        return loadDt;
    }

    public void setLoadDt(Instant loadDt) {
        this.loadDt = loadDt;
    }

    public String getCmnt() {
        return cmnt;
    }

    public void setCmnt(String cmnt) {
        this.cmnt = cmnt;
    }

}
