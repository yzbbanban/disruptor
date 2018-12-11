package com.yzb.study.disruptor.common.tr;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件工具类
 *
 * @author wangban
 * @date 11:33 2018/11/29
 */
public class FileUtil {

    /**
     * 将地址输出到磁盘
     *
     * @param fileName 文件名
     * @param outPath  输出路径
     * @param message  文件内容
     * @return 是否保存成功
     */
    public static boolean outToFile(String fileName, String outPath, String message) {
        // 验证
        if (message == null || message.length() <= 0) {
            return false;
        }
        // 输出文件
        outPath = outPath + "/" + fileName;
        // 检查目录是否存在
        File dir = new File(outPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        SimpleDateFormat format = new SimpleDateFormat("_yyyyMMdd_HH_mm_ss_SSS");
        // 输出路径
        outPath = outPath + "/" + fileName + format.format(new Date()) + ".csv";

        File summarizeFile = new File(outPath);
        OutputStream os = null;
        BufferedWriter out = null;
        try {
            os = new FileOutputStream(summarizeFile, false);
            out = new BufferedWriter(new OutputStreamWriter(os));
            out.write(message);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

}
