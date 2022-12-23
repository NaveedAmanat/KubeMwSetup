package com.idev4.setup.dto.tab;

import com.idev4.setup.domain.MwAnswr;

import java.time.Instant;

public class MwAnswrDto {

    public long answr_seq;

    public String answr_id;

    public String answr_str;

    public long answr_sts_key;

    public long qst_seq;

    public long answr_score;

    public String crtd_by;

    public Instant crtd_dt;

    public String last_upd_by;

    public Instant last_upd_dt;

    public Boolean del_flg;

    public Instant eff_start_dt;

    public Instant eff_end_dt;

    public Boolean crnt_rec_flg;

    public void DomainToDto(MwAnswr answr) {
        answr_seq = answr.getAnswrSeq();
        answr_id = answr.getAnswrId();
        answr_str = answr.getAnswrStr();
        answr_sts_key = answr.getAnswrStsKey();
        qst_seq = answr.getQstSeq();
        answr_score = answr.getAnswrScore();
        crtd_by = answr.getCrtdBy();
        // crtd_dt;
        //
        // public String last_upd_by;
        //
        // public Instant last_upd_dt;
        //
        // public Boolean del_flg;
        //
        // public Instant eff_start_dt;
        //
        // public Instant eff_end_dt;
        //
        // public Boolean crnt_rec_flg;
    }
}
