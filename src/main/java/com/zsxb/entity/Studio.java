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
public class Studio implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "studio_id", type = IdType.AUTO)
    private Integer studioId;

    /** 演出厅名称 **/
    @ApiModelProperty("演出厅名称")
    private String studioName;

    /** 座位行数 **/
    @ApiModelProperty("座位行数")
    private Integer studioRowCount;

    /** 座位列数 **/
    @ApiModelProperty("座位列数")
    private Integer studioColCount;

    /** 演出厅简介 **/
    @ApiModelProperty("演出厅简介")
    private String studioIntroduction;

    /** 说明：	   0：禁用	   1：启用 **/
    @ApiModelProperty("说明：	   0：禁用	   1：启用")
    private Integer studioStatus;


}
