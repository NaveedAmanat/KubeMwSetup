package com.idev4.setup.ids;


import java.io.Serializable;
import java.time.Instant;

/**
 * A MwCommWfMsg.
 */

public class MwCommWfMsgId implements Serializable {

    private static final long serialVersionUID = 1L;


    public Long commWfMsgSeq;

    public Instant effStartDt;
}
