package com.treasure.parttimejob.helper;

import android.os.Environment;

/**
 * ============================================================
 * Author：   treasure
 * time：2019/2/19
 * description:
 * ============================================================
 */

public class Contacts {
    //个人资料
    public static final String USER_NAME= "user_name";
    public static final String USER_NICK= "user_nick";
    public static final String USER_PWD= "user_pwd";
    public static final String USER_IMAGE= "user_image";
    public static final String USER_SIGN = "user_sign";
    public static final String USER_IDENTITY= "user_identity";
    public static final String USER_RESUME= "user_resume";
    public static final String USER_SEX= "user_sex";
    public static final String USER_BIRTH= "user_birth";
    public static final String USER_SCHOOL= "user_school";
    public static final String USER_SCHOOL_YEAR= "user_school_year";
    public static final String USER_WORK= "user_school_year";
    public static final String USER_INTRO= "user_intro";

    //登录状态
    public static final String LOGIN_STATUS="login_status";


    public static final String PHOTO_DIR = Environment.getExternalStorageDirectory() + "/PartTimeJob";
    public static final String CACHE = "/PartTimeJob";

    public static final String INIT_DATA= "init_data";
}
