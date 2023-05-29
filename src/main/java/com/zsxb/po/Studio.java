package com.zsxb.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class Studio implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "studio_id", type = IdType.AUTO)
    private Integer studioId;

    /** 演出厅名称 **/
    private String studioName;

    /** 座位行数 **/
    private Integer studioRowCount;

    /** 座位列数 **/
    private Integer studioColCount;

    /** 演出厅简介 **/
    private String studioIntroduction;

    @TableLogic
    /** 说明：	            0：未删除	            1：删除 **/
    private Integer isDelete;


}
