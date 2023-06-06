package com.zsxb.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zsxb.po.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * ClassName: SaleVO
 * Package: com.zsxb.vo
 * Description:
 *
 * @Author lyh
 * @Create 2023/6/1 1:23
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleVO extends Sale {

    // 雇员用户名
    private String empUid;
    // 顾客用户名
    private String cusUid;
    // 售价
    private BigDecimal saleItemPrice;
    // 演出计划id
    private Integer schedId;


}
