/*
 * Copyright (C), 2022-2022, Freedom Person
 * FileName: CustomerVo.java
 * Author:   lucky
 * Date:     2022/1/18 21:30
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名    修改时间    版本号       描述
 */
package com.wuzf.swing.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 功能描述:<br>
 *
 * @author lucky
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVo {
    //ID
    private int id;

    //姓名
    private String username;

    //职位
    private String jobs;

    //手机号
    private String phone;

    //金钱
    private BigDecimal money;



}
