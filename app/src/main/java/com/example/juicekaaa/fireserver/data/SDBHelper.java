package com.example.juicekaaa.fireserver.data;

/**
 * @author 作者: qiaoshi
 */

import java.io.File;

import android.os.Environment;

/**
 * 数据库根目录 /sdcard/hpcDbData/ 应用的数据建立在 /sdcard/hpcDbData/'包名'/
 */
public class SDBHelper {
    public static final String DB_DIRS = Environment.getExternalStorageDirectory().getPath() + File.separator
            + "Material";
    static {
        while (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        File dbFolder = new File(DB_DIRS);
        // 目录不存在则自动创建目录
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
        }
    }

    static {
        while (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        File dbFolder = new File(DB_DIRS);
        // 目录不存在则自动创建目录
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
        }
    }
}
