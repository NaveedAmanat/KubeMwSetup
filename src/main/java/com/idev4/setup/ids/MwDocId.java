package com.idev4.setup.ids;


import java.io.Serializable;
import java.time.Instant;

/**
 * A MwDoc.
 */

public class MwDocId implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long docSeq;

    public Instant effStartDt;

}
