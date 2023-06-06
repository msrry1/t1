package com.zsxb.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sale_id", type = IdType.AUTO)
    private Long saleId;

    /** 雇员id **/
    private Integer empId;

    /** 顾客id **/
    private Integer cusId;

    // 票id
    private Integer ticketId;


    /** 销售时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date saleTime;

    /** 支付金额 **/
    private BigDecimal salePayment;

    /** 找零 **/
    private BigDecimal saleChange;

    /** 类别取值含义：	            1：销售单	            -1：退款单 **/
    private Integer saleType;

    /** 销售单状态如下：	            0：代付款	            1：已付款 **/
    private Integer saleStatus;

    /** 取值说明：	            1：顾客自购/退票	            0：售票员销售/退票 **/
    private Integer saleSort;

    private Integer playId;


}
