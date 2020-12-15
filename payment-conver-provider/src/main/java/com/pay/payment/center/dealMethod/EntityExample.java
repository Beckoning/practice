
package com.pay.payment.center.dealMethod;

import lombok.Data;

@Data
public class EntityExample {
    private String orderNo;
    private String merchantOrderNo;
    private String opayChannelOrderNo;
    private String outChannelOrderNo;
    private String senderId;
    private String recipientId;
    private String orderStatus;
    private String serviceType;
    private String businessType;
    private String payChannel;
    private String flowType;
    private String otherTrader;
    private String orderExtRecord;
    private String billFinishDate;
    private String senderBillType;
    private String recipientBillType;
    private String recipientAppServiceType;
    private String senderAppServiceType;
    private long senderActualAmount;
    private long recipientActualAmount;
    private long amount;
    private long date;
    private String sourceTableName;
    private String otherTraderEncrypted;
    private String updateTableName;
    private String transactionType;
    private String otherTraderAc;
    private String terminalId;
    private String retrievalReferenceNumber;
    private String clientSource;
    private String outChannelId;
    private String sn;

    public EntityExample(String orderNo) {
        this.orderNo = orderNo;
    }

    public EntityExample() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
