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
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ticket_id", type = IdType.AUTO)
    private Long ticketId;

    /** 座位id **/
    @ApiModelProperty("座位id")
    private Integer seatId;

    /** 演出计划id **/
    @ApiModelProperty("演出计划id")
    private Integer schedId;

    /** 票价 **/
    @ApiModelProperty("票价")
    private BigDecimal ticketPrice;

    /** 含义如下：	            0：待销售	            1：锁定	            9：卖出 **/
    @ApiModelProperty("含义如下：	            0：待销售	            1：锁定	            9：卖出")
    private Integer ticketStatus;

    /** 加锁时间(下单后加锁) **/
    @ApiModelProperty("加锁时间(下单后加锁)")
    private Date ticketLocktime;


}
