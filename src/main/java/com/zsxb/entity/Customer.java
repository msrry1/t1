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
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cus_id", type = IdType.AUTO)
    private Integer cusId;

    /** 顾客姓名 **/
    @ApiModelProperty("顾客姓名")
    private String cusName;

    /** 说明：	            0 女	            1 男 **/
    @ApiModelProperty("说明：	            0 女	            1 男")
    private Integer cusGender;

    /** 顾客电话 **/
    @ApiModelProperty("顾客电话")
    private String cusTelnum;

    /** 顾客邮箱 **/
    @ApiModelProperty("顾客邮箱")
    private String cusEmail;

    /** 顾客用户名 **/
    @ApiModelProperty("顾客用户名")
    private String cusUid;

    /** 登陆密码 **/
    @ApiModelProperty("登陆密码")
    private String cusPwd;

    /** 说明：	            0：禁用	            1：启用 **/
    @ApiModelProperty("说明：	            0：禁用	            1：启用")
    private Integer cusStatus;

    /** 账户余额 **/
    @ApiModelProperty("账户余额")
    private BigDecimal cusBalance;

    /** 支付密码 **/
    @ApiModelProperty("支付密码")
    private String cusPaypwd;


}
