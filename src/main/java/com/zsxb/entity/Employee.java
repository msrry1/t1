package com.zsxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 *
 * @author dz
 * @date 2023-05-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 **/
    @TableId(value = "emp_id", type = IdType.AUTO)
    private Integer empId;

    /** 雇员用户名 **/
    private String empUid;

    /** 雇员类型 **/
    private String type;

    /** 雇员姓名 **/
    private String empName;

    /** 说明：	            0：女	            1：男 **/
    private Integer empGender;

    /** 雇员手机号 **/
    private String empTelnum;

    /** 雇员邮箱 **/
    private String empEmail;

    /** 雇员登录密码 **/
    private String empPwd;

    @TableLogic
    /** 逻辑删除 说明：	            0：未删除	            1：删除 **/
    private Integer isDelete;
}
