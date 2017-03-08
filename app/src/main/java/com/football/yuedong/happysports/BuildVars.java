/*
 * This is the source code of Telegram for Android v. 1.3.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2014.
 */

package com.football.yuedong.happysports;

public class BuildVars {
    public static boolean DEBUG_VERSION = false;
    public static int BUILD_VERSION = 572;
    public static String SERVER = "http://115.159.150.158:8080/";

    public static String SERVER_PITCH_STATUS = SERVER + "yue_sport_app/filed/getFiledPitchsStatus.hl";
    public static String SERVER_Prepaid_Order = SERVER + "yue_sport_app/genPrepaidOrder.hl";
    public static String SERVER_Validate_Order = SERVER + "yue_sport_app/validate_weixin.hl";

    //wechat pay, app id;
    public static final String WX_PAY_APP_ID = "wx24d1e09dfa869a5a";

    /**
     * 微信支付分配的商户号
     */
    public static final String WX_PARTNER_ID = "1218817701";


}
