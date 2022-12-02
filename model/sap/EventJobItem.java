package com.lenovo.sap.api.model.sap;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yuhao5
 * @date 2022/3/12
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class EventJobItem extends BasicJobReqItem{
    private String eventId;
    private String entparm;
    private String trigger_point;

    public EventJobItem(){
        super();
        this.eventId = "";
        this.entparm = "";
        this.trigger_point = "";
    }
}
