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

public class MessagePush {
	
    protected static final String APP_KEY = "a8d001be4f74f1f798923e3a";//步骤6复制的appkey
    protected static final String MASTER_SECRET = "47c1cebba991597a99d27f91";//步骤6复制的Master Secret

    public static void main(String[] args) {
           // String alias = "myalias" ;
            String alias = "123456789a" ;//别名
            String message = "蛋蛋";
            sendPush(alias,message);
        }
    
     
        public static void sendPush(String uid,String message) {
            ClientConfig clientConfig = ClientConfig.getInstance();
            final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
            PushPayload payload = buildPushObject_all_alias_alert(uid,message);
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
                    .setNotification(Notification.alert(message))
                    .setMessage(Message.content(message))
                    .build();
        }

}
