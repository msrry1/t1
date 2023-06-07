package com.zsxb.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sched_id", type = IdType.AUTO)
    private Integer schedId;

    /** 演出厅id **/
    private Integer studioId;

    /** 剧目id **/
    private Integer playId;

    /** 演出时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date schedTime;

    /** 票价 **/
    private BigDecimal schedTicketPrice;

    @TableLogic
    /** 说明：	            0：未删除	            1：删除 **/
    private Integer isDelete;

    // 创建演出计划的管理员id
    private Integer empId;

}
