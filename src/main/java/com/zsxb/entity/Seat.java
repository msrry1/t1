package com.zsxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "seat_id", type = IdType.AUTO)
    private Integer seatId;

    /** 演出厅id **/
    @ApiModelProperty("演出厅id")
    private Integer studioId;

    /** 座位行号 **/
    @ApiModelProperty("座位行号")
    private Integer seatRow;

    /** 座位列号 **/
    @ApiModelProperty("座位列号")
    private Integer seatColumn;

    /** 座位状态	1：有效(默认)	2：过道	 0：损坏 **/
    @ApiModelProperty("座位状态	1：有效(默认)	2：过道	 0：损坏")
    private Integer seatStatus;


}
