package com.innominds.letschat.models;

import com.innominds.letschat.helper.AppDatabase;
import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;

@Table(name = "MessageHistory", database = AppDatabase.class)
public class MessageHistory extends Model {

    @PrimaryKey
    private Long id;

    @Column(name = "msgId")
    public String msgId;

    @Column(name = "senderJitId")
    public String senderJitId;


    @Column(name = "receiverJitId")
    public String receiverJitId;

    @Column(name = "deliveryStatus")
    public String deliveryStatus;

    @Column(name = "timestamp")
    public long timeStamp;


    public MessageHistory() {
    }

    public MessageHistory(String msgId, String senderJitId, String receiverJitId, String deliveryStatus, long timeStamp) {
        this.msgId = msgId;
        this.senderJitId = senderJitId;
        this.receiverJitId = receiverJitId;
        this.deliveryStatus = deliveryStatus;
        this.timeStamp = timeStamp;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSenderJitId() {
        return senderJitId;
    }

    public void setSenderJitId(String senderJitId) {
        this.senderJitId = senderJitId;
    }

    public String getReceiverJitId() {
        return receiverJitId;
    }

    public void setReceiverJitId(String receiverJitId) {
        this.receiverJitId = receiverJitId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
