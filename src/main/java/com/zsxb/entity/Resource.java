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
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "res_id", type = IdType.AUTO)
    private Integer resId;

    /** 父资源id **/
    @ApiModelProperty("父资源id")
    private String resParent;

    /** 资源名称 **/
    @ApiModelProperty("资源名称")
    private String resName;

    /** 资源url **/
    @ApiModelProperty("资源url")
    private String resUrl;


}
