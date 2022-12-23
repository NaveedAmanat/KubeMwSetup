package com.idev4.setup.dto;

public class BranchRemitRelationDto {

    private Long BrnchRemitSeq;
    private Long brnchSeq;
    private Long pymtTypSeq;
    // Added by Areeba - 27-05-2022
    private String remitBankBrnch;
    private String remitIban;

    // Ended by Areeba
    public Long getBrnchRemitSeq() {
        return BrnchRemitSeq;
    }

    public void setBrnchRemitSeq(Long brnchRemitSeq) {
        BrnchRemitSeq = brnchRemitSeq;
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

    public String getRemitBankBrnch() {
        return remitBankBrnch;
    }

    public void setRemitBankBrnch(String remitBankBrnch) {
        this.remitBankBrnch = remitBankBrnch;
    }

    public String getRemitIban() {
        return remitIban;
    }

    public void setRemitIban(String remitIban) {
        this.remitIban = remitIban;
    }
}
