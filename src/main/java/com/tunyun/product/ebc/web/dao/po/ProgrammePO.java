package com.tunyun.product.ebc.web.dao.po;

import java.util.List;

/**
 * Created by shiliang on 2016/12/14.
 */
public class ProgrammePO {
    private long prog_id;
    private String prog_name;
    private double prog_score;
    private long stat_time;
    private List<ProgrammeAttrPO> attrs;

    public long getProg_id() {
        return prog_id;
    }

    public void setProg_id(long prog_id) {
        this.prog_id = prog_id;
    }

    public String getProg_name() {
        return prog_name;
    }

    public void setProg_name(String prog_name) {
        this.prog_name = prog_name;
    }

    public double getProg_score() {
        return prog_score;
    }

    public void setProg_score(double prog_score) {
        this.prog_score = prog_score;
    }

    public long getStat_time() {
        return stat_time;
    }

    public void setStat_time(long stat_time) {
        this.stat_time = stat_time;
    }

    public List<ProgrammeAttrPO> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<ProgrammeAttrPO> attrs) {
        this.attrs = attrs;
    }
}
