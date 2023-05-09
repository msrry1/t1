package com.zsxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
@ApiModel(value = "", description = "")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sale_id", type = IdType.AUTO)
    private Long saleId;

    /** 雇员id **/
    @ApiModelProperty("雇员id")
    private Integer empId;

    /** 顾客id **/
    @ApiModelProperty("顾客id")
    private Integer cusId;

    /** 销售时间 **/
    @ApiModelProperty("销售时间")
    private Date saleTime;

    /** 支付金额 **/
    @ApiModelProperty("支付金额")
    private BigDecimal salePayment;

    /** 找零 **/
    @ApiModelProperty("找零")
    private BigDecimal saleChange;

    /** 类别取值含义：	            1：销售单	            -1：退款单 **/
    @ApiModelProperty("类别取值含义：	            1：销售单	            -1：退款单")
    private Integer saleType;

    /** 销售单状态如下：	            0：代付款	            1：已付款 **/
    @ApiModelProperty("销售单状态如下：	            0：代付款	            1：已付款")
    private Integer saleStatus;

    /** 取值说明：	            1：顾客自购/退票	            0：售票员销售/退票 **/
    @ApiModelProperty("取值说明：	            1：顾客自购/退票	            0：售票员销售/退票")
    private Integer saleSort;


}
