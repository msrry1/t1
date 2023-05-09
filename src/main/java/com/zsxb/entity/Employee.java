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
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "emp_id", type = IdType.AUTO)
    private Integer empId;

    /** 数据字典id **/
    @ApiModelProperty("数据字典id")
    private Integer dictId;

    /** 雇员编号 **/
    @ApiModelProperty("雇员编号")
    private String empNo;

    /** 雇员姓名 **/
    @ApiModelProperty("雇员姓名")
    private String empName;

    /** 说明：	            0：女	            1：男 **/
    @ApiModelProperty("说明：	            0：女	            1：男")
    private Integer empGender;

    /** 雇员手机号 **/
    @ApiModelProperty("雇员手机号")
    private String empTelnum;

    /** 雇员邮箱 **/
    @ApiModelProperty("雇员邮箱")
    private String empEmail;

    /** 雇员登录密码 **/
    @ApiModelProperty("雇员登录密码")
    private String empPwd;

    /** 说明：	            0：禁用	            1：启用 **/
    @ApiModelProperty("说明：	            0：禁用	            1：启用")
    private Integer empStatus;


}
