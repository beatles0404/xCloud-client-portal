package com.lenovo.sap.api.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lenovo.sap.api.model.history.HistoryType;
import com.lenovo.sap.api.model.history.HistoryVO;
import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @author yuhao5
 * @date 6/27/22
 **/
@Data
public class RequestVO {
    private Integer         id;
    private String          requestNo;
    private HistoryType type;
    private RequestStatus   status;
    private String          requester;
    private List<HistoryVO> params;
    private Instant createdAt;
    private String          createdBy;
    private Instant         updatedAt;
    private String          updatedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

}
