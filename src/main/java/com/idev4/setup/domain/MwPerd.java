package com.idev4.setup.domain;

import com.idev4.setup.ids.MwPerdId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwPrd.
 */
@Entity
@Table(name = "mw_perd")
@IdClass(MwPerdId.class)
public class MwPerd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PERD_KEY")
    private Long perdKey;

    @Id
    @Column(name = "PERD_STRT_DT")
    private Instant perdStrtDt;

    @Column(name = "PERD_END_DT")
    private Instant perdEndDt;

    @Column(name = "QTR_KEY")
    private Long qtrKey;

    @Column(name = "QTR_STRT_DT")
    private Instant qtrStrtDt;

    @Column(name = "QTR_END_DT")
    private Instant qtrEndDt;

    @Column(name = "BI_ANL_KEY")
    private Long biAnlKey;

    @Column(name = "BI_ANL_STRT_DT")
    private Instant biAnlStrtDt;

    @Column(name = "BI_ANL_END_DT")
    private Instant biAnlEndDt;

    @Column(name = "FIN_YR")
    private Long finDt;

    public Long getPerdKey() {
        return perdKey;
    }

    public void setPerdKey(Long perdKey) {
        this.perdKey = perdKey;
    }

    public Instant getPerdStrtDt() {
        return perdStrtDt;
    }

    public void setPerdStrtDt(Instant perdStrtDt) {
        this.perdStrtDt = perdStrtDt;
    }

    public Instant getPerdEndDt() {
        return perdEndDt;
    }

    public void setPerdEndDt(Instant perdEndDt) {
        this.perdEndDt = perdEndDt;
    }

    public Long getQtrKey() {
        return qtrKey;
    }

    public void setQtrKey(Long qtrKey) {
        this.qtrKey = qtrKey;
    }

    public Instant getQtrStrtDt() {
        return qtrStrtDt;
    }

    public void setQtrStrtDt(Instant qtrStrtDt) {
        this.qtrStrtDt = qtrStrtDt;
    }

    public Instant getQtrEndDt() {
        return qtrEndDt;
    }

    public void setQtrEndDt(Instant qtrEndDt) {
        this.qtrEndDt = qtrEndDt;
    }

    public Long getBiAnlKey() {
        return biAnlKey;
    }

    public void setBiAnlKey(Long biAnlKey) {
        this.biAnlKey = biAnlKey;
    }

    public Instant getBiAnlStrtDt() {
        return biAnlStrtDt;
    }

    public void setBiAnlStrtDt(Instant biAnlStrtDt) {
        this.biAnlStrtDt = biAnlStrtDt;
    }

    public Instant getBiAnlEndDt() {
        return biAnlEndDt;
    }

    public void setBiAnlEndDt(Instant biAnlEndDt) {
        this.biAnlEndDt = biAnlEndDt;
    }

    public Long getFinDt() {
        return finDt;
    }

    public void setFinDt(Long finDt) {
        this.finDt = finDt;
    }

}
