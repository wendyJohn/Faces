package com.example.juicekaaa.fireserver.net;

/**
 * @author qiaoshi
 */
public class URLs {
    public static boolean m_bCopyDB = true;

//    public static String HOST_IP = "10.101.80.177";
//    public static String HOST_PORT = "8080";
//    public static String HOST = "http://" + HOST_IP + ":" + HOST_PORT;

    public static String HOST = "https://slyj.slicity.com";
    //加载图片资源
    public static String IMAGEE_URL = HOST + "/kspf/app/publicityedu/banner?platformkey=app_firecontrol_owner";
    //加载视频资源
    public static String Article_URL = HOST + "/kspf/app/publicityedu/list";
    // 一键求救
    public static String CryForHelp_URL = HOST + "/kspf/app/stationassistant/add";
    //应急物资
    public static String Materials_URL = HOST + "/kspf/app/station/count";

    //加载视频资源
    public static String Format_URL = HOST + "/kspf/app/station/list";

    //FID盘点出入库
    public static String Submission_URL = HOST + "/kspf/app/station/state";
    //FID盘点位置信息
    public static String StorageLocation_URL = HOST + "/kspf/app/station/notice";
    //历史记录
    public static String HISTORRECORD_URL = HOST + "/kspf/app/station/record";
}
