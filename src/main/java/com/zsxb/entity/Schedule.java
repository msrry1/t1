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
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sched_id", type = IdType.AUTO)
    private Integer schedId;

    /** 演出厅id **/
    @ApiModelProperty("演出厅id")
    private Integer studioId;

    /** 剧目id **/
    @ApiModelProperty("剧目id")
    private Integer playId;

    /** 演出时间 **/
    @ApiModelProperty("演出时间")
    private Date schedTime;

    /** 票价 **/
    @ApiModelProperty("票价")
    private BigDecimal schedTicketPrice;

    /** 演出计划状态	0：编辑(默认)	1：执行	-1：取消 **/
    @ApiModelProperty("演出计划状态	0：编辑(默认)	1：执行	-1：取消")
    private Integer schedStatus;


}
