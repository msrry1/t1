package com.zsxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "", description = "")
public class Play implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "play_id", type = IdType.AUTO)
    private Integer playId;

    /** 剧目类型id **/
    @ApiModelProperty("剧目类型id")
    private Integer dictTypeId;

    /** 剧目语言id **/
    @ApiModelProperty("剧目语言id")
    private Integer dictLangId;

    /** 剧目名称 **/
    @ApiModelProperty("剧目名称")
    private String playName;

    /** 剧目简介 **/
    @ApiModelProperty("剧目简介")
    private String playIntroduction;

    /** 剧目图片（路径） **/
    @ApiModelProperty("剧目图片（路径）")
    private String playImage;

    /** 剧目媒体（路径） **/
    @ApiModelProperty("剧目媒体（路径）")
    private String playVideo;

    /** 剧目时长 **/
    @ApiModelProperty("剧目时长")
    private Integer playLength;

    /** 剧目票价 **/
    @ApiModelProperty("剧目票价")
    private BigDecimal playTicketPrice;

    /** 取值含义：	            0：待安排演出	            1：已安排演出	            -1：下线 **/
    @ApiModelProperty("取值含义：	            0：待安排演出	            1：已安排演出	            -1：下线")
    private Integer playStatus;


}
