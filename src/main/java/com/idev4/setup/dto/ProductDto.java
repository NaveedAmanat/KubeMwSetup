package com.idev4.setup.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    public Long productSeq;

    public String productName;

    public int installments;

    public String calcType;

    public double serviceCharges;

    public double minAmount;

    public double maxAmount;

    public String condition;

    public List<Charges> charges = new ArrayList<Charges>();

    public long clientSeq;

    public long portSeq;

    public String prdNm;

    public long loanAppSeq;

    public Long prdGrpSeq;

    public String prdId;

    public String prdCmnt;

    public Long prdStsKey;

    public Long prdTypKey;

    public Boolean irrFlg;

    public Long roundingScale;

    public Long roundingAdjustment;

    public Boolean dailyAccuralFlg;

    public Long fndByKey;

    public Long crncyCdKey;

    public Double irrVal;

    public Boolean mltLoanFlg;

    public Long rndngScl;

    public Long rndngAdj;

    public String productStatus;

    public long prdRul;

    public long limitRule;

    public long termRule;

    public long prdSeq;

    public Integer pdcNum;

}
