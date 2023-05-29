package com.zsxb.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ticket_id", type = IdType.AUTO)
    private Long ticketId;

    /** 座位id **/
    private Integer seatId;

    /** 演出计划id **/
    private Integer schedId;

    /** 票价 **/
    private BigDecimal ticketPrice;

    /** 含义如下：	            0：待销售	            1：锁定	            9：卖出 **/
    private Integer ticketStatus;

    /** 加锁时间(下单后加锁) **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date ticketLocktime;


}
