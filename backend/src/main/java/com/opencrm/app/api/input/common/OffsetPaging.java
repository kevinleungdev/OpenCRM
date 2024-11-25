package com.opencrm.app.api.input.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OffsetPaging {
    private Integer limit;
    private Integer offset;
}
