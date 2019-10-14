package com.newland.intelligentdaysystem;

public class Constant {
    public static String IP_ADRESS = "ipAddress";
    public static String PORT = "port";

    public static String DEFAULT_IP_ADRESS = "120.77.58.34";
    public static String DEFAULT_PORT = "8800";
    /**
     * 连接IP  key
     */
    public static String SP_CONNECT_IP = "sp_connect_ip";

    /**
     * 连接PORT
     */
    public static String SP_CONNECT_PORT =  "sp_connect_port";

    /**
     * 延迟时间key'
     */
    public static String SP_DELAYED_VALUE = "sp_delayed_value";

    /**
     * 服务器重连
     */
    public static int EVENT_SERVER_RECONNECT = 1002;

    /**
     * 服务器停止重连
     */
    public static final int EVENT_SERVER_STOPCONNECT = 1003;
    /**
     * 断开与服务器连接
     */
    public static final int EVENT_SERVER_OUT_CONNECT = 1004;

    /**
     * 服务器连接上
     */
    public static final int EVENT_SERVER_CONNECT = 1005;

    /**
     * 设置端口和IP
     */
    public static final int EVENT_SERVER_IP_PORT = 1006;

    /**
     * 连接服务器,异常
     */
    public static final int EVENT_SERVER_ERR = 1007;

    /**
     * 修改端口
     */
    public static int EVENT_EDIT_PROT = 1008;

    /**
     * 修改IP
     */
    public static int EVENT_EDIT_IP = 1009;

    /**
     * 修改延迟时间
     */
    public static int EVENT_EDIT_TIME = 1010;

    /**
     * 开灯
     */
    public static int EVENT_OPEN_LAMP = 1011;
    /**
     * 开灯关结果
     */
    public static int EVENT_OPEN_LAMP_RESULT = 1012;
    /**
     * 关灯
     */
    public static int EVENT_CLOSE_LAMP = 1013;
    /**
     * 关灯结果
     */
    public static int EVENT_CLOSE_LAMP_RESULT = 1014;
    /**
     * 控制模式
     */
    public static int EVENT_CTRL_STATE = 1015;
    /**
     * 控制模式
     */
    public static int EVENT_CHANGE_CTRL_STATE = 1016;
    /**
     * 错误提示
     */
    public static int EVENT_ERR = 1017;
    /**
     * 错误提示
     */
    public static int EVENT_PERSON_IR = 1018;

    //截图存放路径
    public static final String ERROR_LOG_FILE = "/mnt/sdcard/newlandSmartHome/log/";

    /*云平台属性SP配置*/
    public final static String SETTING_GATEWAY_TAG = "SETTING_GATEWAY_TAG";
    public final static String SETTING_PLATFORM_ADDRESS = "SETTING_PLATFORM_ADDRESS";
    public final static String SETTING_PORT = "SETTING_PORT";
    public final static String DEVICE_ID = "DEVICE_ID";
    public final static String LOGIN_USER_NAME = "LOGIN_USER_NAME";
    public final static String LOGIN_PWD = "LOGIN_PWD";
    public final static String BASE_URL = "BASE_URL";
    public final static String ACCESS_TOKEN = "ACCESS_TOKEN";

    public final static int BOX_STATUS_CLOSE = 0;
    public final static int BOX_STATUS_OPEN = 1;
    public final static int BOX_STATUS_ALARM = 2;

    public final static String ANGLEX_MAX = "ANGLEX_MAX";
    public final static String ANGLEX_MIN = "ANGLEX_MIN";
    public final static String ANGLEY_MAX = "ANGLEY_MAX";
    public final static String ANGLEY_MIN = "ANGLEY_MIN";
    public static String ANGLEX_MAX_DEFAULT_VALUE = "180";
    public static String ANGLEX_MIN_DEFAULT_VALUE = "0";
    public static String ANGLEY_MAX_DEFAULT_VALUE = "60";
    public static String ANGLEY_MIN_DEFAULT_VALUE = "15";
    public static String IP_DEFAULT_VALUE = "api.nlecloud.com";
    public static String PORT_DEFAULT_VALUE = "80";

}
