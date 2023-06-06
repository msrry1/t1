package com.zsxb.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "seat_id", type = IdType.AUTO)
    private Integer seatId;

    /** 演出厅id **/
    private Integer studioId;

    /** 座位行号 **/
    private Integer seatRow;

    /** 座位列号 **/
    private Integer seatColumn;
}
