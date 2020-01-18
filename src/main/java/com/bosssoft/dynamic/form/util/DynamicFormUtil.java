package com.bosssoft.dynamic.form.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DynamicFormUtil {

    /**
     * 获取当期时间
     * 
     * @return 返回字符串形式
     */
    public static String getCurrentTimeStr() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    /**
     * 生成ID
     * 
     * @return
     */
    public static long generateId() {
        // 取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        // long millis = System.nanoTime();
        // 加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        // 如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        long id = new Long(str);
        return id;

    }
    
}
