package com.zsxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 顾客表
 *
 * @author dz
 * @date 2023-05-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cus_id", type = IdType.AUTO)
    private Integer cusId;

    /** 顾客姓名 **/
    private String cusName;

    /** 说明：	            0 女	            1 男 **/
    private Integer cusGender;

    /** 顾客电话 **/
    private String cusTelnum;

    /** 顾客邮箱 **/
    private String cusEmail;

    /** 顾客用户名 **/
    private String cusUid;

    /** 登陆密码 **/
    private String cusPwd;

    @TableLogic
    /** 说明：	            0：未删除	            1：删除 **/
    private Integer isDelete;

    /** 账户余额 **/
    private BigDecimal cusBalance;

    /** 支付密码 **/
    private String cusPaypwd;


}
