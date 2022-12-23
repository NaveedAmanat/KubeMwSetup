package com.idev4.setup.dto;

import com.idev4.setup.domain.MwCmnty;
import com.idev4.setup.domain.MwPort;
import com.idev4.setup.domain.MwPrd;
import com.idev4.setup.domain.MwUc;

import java.util.List;

/*
    Authored by Areeba
    Branch Setup
    27-05-2022
*/

public class BranchOpeningDto {

    //Branch
    public long brnchSeq;
    public long areaSeq;
    public String brnchNm;
    public String brnchDscr;
    public long brnchStsKey;
    public long brnchTypKey;
    public String brnchPhNum;
    public String email;
    public long mfcibCmpnySeq;
    public String hrLocCd;
    public java.util.Date mobStrtDt;
    public java.util.Date mobEndDt;

    //Branch Acct Set
    public long brnchAcctSetSeq;
    public String bankNm;
    public String bankBrnch;
    public String acctNm;
    public String acctNum;
    public String iban;
    public String bankCode;
    public String ibftBankCode;

    //Branch Location Rel
    public long citySeq;

    //Branch Product Rel
    public List<MwPrd> products;

    //Branch Remit Rel
    public long brnchRemitSeq;
    public long pymtTypSeq;
    public String remitBankBrnch;
    public String remitIban;

    //Address
    public String hseNum;
    public String strt;
    public String othdtl;
    public long addrTypKey;
    public long ucSeq;
    public long cmntySeq;
    public String vlg;
    public Double longi;
    public Double lati;

    //Port
    public List<MwPort> ports;

    //Cmnty
    public List<MwCmnty> communities;

    //UC
    public List<MwUc> ucs;

}
