package com.tunyun.product.ebc.web.dao.po;

import com.tunyun.product.ebc.bgmanager.po.AliasPO;

import java.util.List;

/**
 * Created by shiliang on 2016/12/14.
 */
public class ProgrammeAttrPO {
    private long prog_id;
    private long p_id;
    private long v_id;
    private long stat_time;
    private String p_name;
    private String v_name;
    private List<AliasPO> valias;

    public long getProg_id() {
        return prog_id;
    }

    public void setProg_id(long prog_id) {
        this.prog_id = prog_id;
    }

    public long getP_id() {
        return p_id;
    }

    public void setP_id(long p_id) {
        this.p_id = p_id;
    }

    public long getV_id() {
        return v_id;
    }

    public void setV_id(long v_id) {
        this.v_id = v_id;
    }

    public long getStat_time() {
        return stat_time;
    }

    public void setStat_time(long stat_time) {
        this.stat_time = stat_time;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public List<AliasPO> getValias() {
        return valias;
    }

    public void setValias(List<AliasPO> valias) {
        this.valias = valias;
    }
}
