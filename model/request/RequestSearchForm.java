package com.lenovo.sap.api.model.request;

import com.lenovo.sap.api.model.history.HistoryType;
import com.lenovo.sap.api.model.history.HistoryVO;
import com.lenovo.xframe.orm.jooq.SearchForm;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * @author yuhao5
 * @date 6/23/22
 **/
@Data
public class RequestSearchForm implements SearchForm {

    @Keyword(
            "request_no"
    )
    private String q;

    @Equal
    @EnableOrder
    private Integer id;

    @In("id")
    private List<Integer> ids;

    @Equal
    @EnableOrder
    private String requestNo;

    @Equal
    @EnableOrder
    private HistoryType     type;

    @Equal
    @EnableOrder
    private RequestStatus   status;

    @Equal
    @EnableOrder
    private String          requester;

    @EnableOrder
    private Instant createdAt;
    @EnableOrder
    private String          createdBy;
    @EnableOrder
    private Instant         updatedAt;
    @EnableOrder
    private String          updatedBy;
}
