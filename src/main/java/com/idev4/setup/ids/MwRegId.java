package com.idev4.setup.ids;


import java.io.Serializable;
import java.time.Instant;

/**
 * A MwReg.
 */

public class MwRegId implements Serializable {

    private static final long serialVersionUID = 1L;


    public Long regSeq;

    public Instant effStartDt;
}
