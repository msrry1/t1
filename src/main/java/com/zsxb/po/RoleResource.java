package com.zsxb.po;

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
@TableName("role_resource")
@ApiModel(value = "", description = "")
public class RoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_res_id", type = IdType.AUTO)
    private Integer roleResId;

    /** 角色id **/
    @ApiModelProperty("角色id")
    private Integer roleId;

    /** 资源id **/
    @ApiModelProperty("资源id")
    private Integer resId;


}
