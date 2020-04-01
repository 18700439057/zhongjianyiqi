package com.sibo.business.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import java.util.HashMap;

public class PushExample {

    protected static final String APP_KEY = "c3e74e684692081aa4cbf3ac";
    protected static final String MASTER_SECRET = "81a71889c8875912a9e76495";

    public static void main(String[] args) {
        //String alias = "myalias" ;
        for (int i=0;i<10;i++) {
            String alias = "123456789a" ;
            String message = "测试alert消息";
            testSendPush(alias,message);
        }

    }

    public static void testSendPush(String alias,String message) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        PushPayload payload = buildPushObject_all_alias_alert(alias,message);
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println(result);
        } catch (APIConnectionException e) {
//            LOG.error("Connection error. Should retry later. ", e);
//            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
//            LOG.error("Error response from JPush server. Should review and fix it. ", e);
//            LOG.info("HTTP Status: " + e.getStatus());
//            LOG.info("Error Code: " + e.getErrorCode());
//            LOG.info("Error Message: " + e.getErrorMessage());
//            LOG.info("Msg ID: " + e.getMsgId());
//            LOG.error("Sendno: " + payload.getSendno());
        }
    }

    public static PushPayload buildPushObject_all_alias_alert(String alias,String message) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                //.setNotification(Notification.alert(message))
                .setNotification(Notification.android(message,"中间西部仪器管理系统消息",null))
                .setMessage(Message.newBuilder().setTitle("系统消息").setMsgContent("晚上好").addExtra("1","2").build())
                .build();
    }
}
