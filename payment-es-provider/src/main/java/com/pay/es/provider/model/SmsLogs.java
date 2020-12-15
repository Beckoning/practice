package com.pay.es.provider.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmsLogs {

    private String id;
    private Date createDate;
    //发送时间
    private Date sendDate;
    //发送长号码
    private String longCode;
    //下发手机号
    private String mobile;
    //发送公司名称
    private String corpName;
    //下发短信内容
    private String smsContent;
    //短信发送状态 0成功 1失败
    private Integer status;
    //运营编号ID 0移动 1联通 2电信
    private Integer operatorId;
    //省份
    private String province;
    //下发服务器Ip地址
    private String ipAddr;
    //短信状态报告f返回时长
    private Integer replyTotal;
    //费率
    private Integer free;


}
