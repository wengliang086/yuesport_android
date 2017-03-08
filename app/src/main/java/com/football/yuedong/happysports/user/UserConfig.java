package com.football.yuedong.happysports.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.football.yuedong.happysports.ApplicationLoader;
import com.football.yuedong.happysports.domain.User;
import com.football.yuedong.happysports.utils.FileLog;

import java.io.File;

/**
 * Created by liuyanjun on 2015/12/14.
 */
public class UserConfig {

    private static User currentUser;

    private final static Object sync = new Object();

    public static String ACCESS_TOKEN = "";

    public static void setCurrentUser(User user) {
        synchronized (sync) {
            currentUser = user;
        }
    }

    public static boolean isClientActivated() {
        synchronized (sync) {
            return currentUser != null;
        }
    }


    public static User getCurrentUser() {
        synchronized (sync) {
            return currentUser;
        }
    }

    public static String getClientUserId() {
        synchronized (sync) {
            return currentUser != null ? currentUser.value.uid : "";
        }
    }

    public static void saveConfig(boolean withFile) {
        synchronized (sync) {
            try {
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if (currentUser != null) {
                    if (withFile) {
                        String data = JSON.toJSONString(currentUser);
                        String userString = Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
                        editor.putString("user", userString);
                    }
                } else {
                    editor.remove("user");
                }

                editor.commit();
            } catch (Exception e) {
                FileLog.e("userconfig", e);
            }
        }
    }


    public static void loadConfig() {
        synchronized (sync) {
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("userconfing", Context.MODE_PRIVATE);
                String user = preferences.getString("user", null);
                if (user != null) {
                    byte[] userBytes = Base64.decode(user, Base64.DEFAULT);
                    if (userBytes != null) {
                        currentUser = JSON.parseObject(userBytes, User.class);
                    }
                }
            }
    }


    public static void clearConfig() {
        currentUser = null;
        saveConfig(true);
    }



}
