package com.zsxb.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * ClassName: salePerfoemance
 * Package: com.zsxb.vo
 * Description:
 *
 * @Author lyh
 * @Create 2023/6/5 9:43
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleCount {

    // 剧目名
    private String playName;
    // 剧目状态
    private Integer playStatus;
    // 总销售额
    private BigDecimal saleCount;
}
