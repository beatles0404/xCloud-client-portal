package com.lenovo.sap.api.model.history;

import com.lenovo.xframe.orm.jooq.SearchForm;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * @author yuhao5
 * @date 6/23/22
 **/
@Data
public class HistorySearchForm implements SearchForm {
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
    private HistoryType type;

    @Equal
    @EnableOrder
    private HistoryStatus status;

    @EnableOrder
    private Instant createdAt;
    @EnableOrder
    private String        createdBy;
    @EnableOrder
    private Instant       updatedAt;
    @EnableOrder
    private String        updatedBy;
}
