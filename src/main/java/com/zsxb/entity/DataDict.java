package com.zsxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("data_dict")
@ApiModel(value = "", description = "")
public class DataDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dict_id", type = IdType.AUTO)
    private Integer dictId;

    /** 父id **/
    @ApiModelProperty("父id")
    private Integer superDictId;

    /** 同级顺序 **/
    @ApiModelProperty("同级顺序")
    private Integer dictIndex;

    /** 数据字典名称 **/
    @ApiModelProperty("数据字典名称")
    private String dictName;

    /** 数据字典值 **/
    @ApiModelProperty("数据字典值")
    private String dictValue;


}
