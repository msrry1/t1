package com.zsxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
@Data
@TableName("sale_item")
@ApiModel(value = "", description = "")
public class SaleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sale_item_id", type = IdType.AUTO)
    private Long saleItemId;

    /** 票id **/
    @ApiModelProperty("票id")
    private Long ticketId;

    /** 销售单id **/
    @ApiModelProperty("销售单id")
    private Long saleId;

    /** 售价 **/
    @ApiModelProperty("售价")
    private BigDecimal saleItemPrice;


}
