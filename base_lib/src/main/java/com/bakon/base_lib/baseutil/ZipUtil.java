package com.bakon.base_lib.baseutil;

import android.text.TextUtils;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;

public class ZipUtil {

    /**
     * 解压文件，不设置密码
     * @param zipFilePath 被解压的文件路径（完整路径）
     * @param unzipFilePath 解压后文件保存的路径 （文件的输出路径）
     */
    public static void unZip (String zipFilePath , String unzipFilePath) {
        unZip(zipFilePath , unzipFilePath , "");
    }

    /**
     * 解压文件，支持中文路径已经文件名
     * @param zipFilePath 压缩文件路径（完整路径）
     * @param unzipFilePath 解压后输出的路径（文件的输出路径）
     * @param password 解压密码
     */
    public static void unZip (String zipFilePath , String unzipFilePath , String password) {
        try {
            File zipFile = new File(zipFilePath);
            if(zipFile.exists()) {
                net.lingala.zip4j.core.ZipFile zip4j = new net.lingala.zip4j.core.ZipFile(zipFile);
                if(zip4j.isEncrypted() && !TextUtils.isEmpty(password)) {
                    zip4j.setPassword(password);
                }
                zip4j.extractAll(unzipFilePath);
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件，无密码压缩
     * @param zipFilePath 压缩后zip包的存储路径  （完整路径）
     * @param rawfilePatn 被压缩的文件  （文件的输出路径）
     */
    public static void Zip (String zipFilePath , String rawfilePatn ) {
        Zip(zipFilePath , rawfilePatn , "");
    }

    /**
     * 压缩一个文件
     * @param zipFilePath 压缩存储的路径 （完整路径）
     * @param rawfilePatn 被压缩的文件 （文件的输出路径）
     * @param password 压缩密码
     */
    public static void Zip (String zipFilePath , String rawfilePatn , String password) {
        try {
            // 创建一个zip包
            net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(zipFilePath);
            ArrayList<File> fileAddZip = new ArrayList<File>();   // 向zip包中添加文件集合
            fileAddZip.add(new File(rawfilePatn));                // 向zip包中添加一个pdf文件
            ZipParameters parameters = new ZipParameters(); 	  // 设置zip包的一些参数集合

            if(!TextUtils.isEmpty(password)) {
                parameters.setEncryptFiles(true); // 是否设置密码（此处设置为：是）
                parameters.setPassword(password); // 压缩包密码
            }
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);        // 压缩方式(默认值)
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 普通级别（参数很多）
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);  // 加密级别

            zipFile.createZipFile(fileAddZip , parameters);       // 创建压缩包完成
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

}
