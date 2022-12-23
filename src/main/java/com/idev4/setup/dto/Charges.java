package com.idev4.setup.dto;

import com.idev4.setup.domain.MwPrdChrgSlb;

import java.util.List;

public class Charges {

    public long type;

    public long charge;
    public List<MwPrdChrgSlb> slbs;

    public Charges(long type, long charge) {
        super();
        this.type = type;
        this.charge = charge;
    }

}
