package com.cccis.base.common;

import static com.cccis.base.utils.ConstantUtils.getValue;

/**
 * Job常量
 */
public class BaseConstant {

    /**
     * 默认文件字符编码
     */
    public static final String COMMA = getValue(",");

    /**
     * 默认文件字符编码
     */
    public static final String ENCODING = getValue("UTF-8");

    /**
     * batch任务执行完成
     */
    public static final String BATCH_TASK_EXECUTION_DONE = getValue("done");

    /**
     * batch任务执行错误
     */
    public static final String BATCH_TASK_EXECUTION_ERROR = getValue("error");

    /**
     * 默认客户端Service的bean方法名称.
     */
    public static final String DEFAULT_CLIENT_SERVICE_METHOD_NAME = getValue("execute");

    /**
     * 消息发送Service.
     */
    public static final String MESSAGE_SENDING_SERVICE_BEAN_NAME = getValue("messageService");

    /**
     * 邮件发送Service.
     */
    public static final String MAIL_SENDING_SERVICE_BEAN_NAME = getValue("mailService");

    /**
     * 邮件Host的配置项名称.
     */
    public static final String EMAIL_HOST = "mail.host";

    /**
     * 邮件用户名称.
     */
    public static final String EMAIL_USERNAME = "mail.username";

    /**
     * 邮件密码.
     */
    public static final String EMAIL_PASSWORD = "mail.password";

    /**
     * 系统能否发送邮件
     */
    public static final String EMAIL_ENABLED = "mail.enabled";

    /**
     * 系统能否发送信息
     */
    public static final String MESSAGE_ENABLED = "message.enabled";

    /**
     * 异常最大长度.
     */
    public static final int EXCEPTION_MAX_LENGTH = getValue(4000);

    /**
     * 消息发送成功
     */
    public static final Integer SEND_SUCCESS = 0;

    /**
     * 默认任务版本号.
     */
    public static final Long DEFAULT_TASK_VERSION = 0L;


    // Http URL to send sms.
    public final static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";

    // query the user info
    public final static String URI_GET_USER_INFO = "http://yunpian.com/v1/user/get.json";

    // API key for sms account.
    public final static String APIKEY = "be657b732924414a38f6521e0a04bff0";

    // alarm balance
    public static final Integer ALARM_BALANCE = 100;

    // expected size
    public static final int EXPECTED_SIZE = 3;

    // alarm user content code
    public static final int ALARM_USER_CODE = 10;

    // 网络请求异常
    public static final int NET_ERROR = 99;

    // 网络请求异常消息
    public static final String NET_ERROR_MESSAGE = "网络请求异常。";

    /**
     * 短信平台余额提醒
     */
    public final static String ALARM_BALANCE_SUBJECT = "短信平台余额提醒";

    /**
     * 剩余额快用完
     */
    public final static String ALARM_BALANCE_CONTENT = "【DRP平台】短信平台所剩余额快用完了，请联系供应商购买更多短信额度。https://www.yunpian.com";

    /**
     * 短信平台故障
     */
    public final static String ALARM_SMS_SUBJECT = "短信平台故障";

    /**
     * 短信平台出现异常
     */
    public final static String ALARM_SMS_CONTENT = "【DRP平台】短信平台出现异常，请联系供应商。你也可以通过短信平台网站https://www.yunpian.com/4.0/api/recode.html 查看错误信息，发送异常的代码为";

    /**
     * 黑名单用户提醒
     */
    public final static String ALARM_USER_SUBJECT = "短信平台黑名单用户提醒";

    /**
     * 解除黑名
     */
    public final static String ALARM_USER_CONTENT =
            "【CAR WISE平台】手机号码为{0}的客户是短信平台的黑名单用户，请联系短信平台的客服解除黑名单。\n" + "解除方法为：需要您提供账户ID（注册账户或apikey前8位）+ 要解除黑名单的手机号。详情见：https://www.yunpian.com/start/faq.html \n\n" + "造成黑名单的原因如下:\n"
                    + "1) 之前投诉过运营商，如打过10086、10010或10000投诉的，可能会被运营商加入黑名单。\n" + "2) 有过退订历史，如回复过含有T、TD、退订或取消等代表拒绝接收短信的指令。";

}
