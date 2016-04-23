package com.ohdroid.zbmaster.utils

/**
 * Created by ohdroid on 2016/4/23.
 */

import android.os.Environment
import java.io.*

/**
 * SD卡相关的辅助类
 *
 *
 *
 */
class SDCardUtils {
    companion object {

        val SD_PIC = Environment.getExternalStorageDirectory().absolutePath + File.separator + Environment.DIRECTORY_PICTURES

        /**
         * 判断SDCard是否可用
         *
         * @return
         */

        fun isSDCardEnable(): Boolean {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);

        }

        /**
         * 获取SD卡路径
         *
         * @return
         */
        fun getSDCardPath(): String {
            return Environment.getExternalStorageDirectory().absolutePath + File.separator;
        }

        /**
         * 获取SD卡的剩余容量 单位byte
         *
         * @return
         */
        //        fun getSDCardAllSize():Long
        //        {
        //            if (isSDCardEnable()) {
        //                StatFs stat = new StatFs(getSDCardPath());
        //                // 获取空闲的数据块的数量
        //                long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        //                // 获取单个数据块的大小（byte）
        //                long freeBlocks = stat.getAvailableBlocks();
        //                return freeBlocks * availableBlocks;
        //            }
        //            return 0;
        //        }

        /**
         * 获取指定路径所在空间的剩余可用容量字节数，单位byte
         *
         * @param filePath
         * @return 容量字节 SDCard可用空间，内部存储可用空间
         */
        //        fun getFreeBytes(String filePath):Long
        //        {
        //            // 如果是sd卡的下的路径，则获取sd卡可用容量
        //            if (filePath.startsWith(getSDCardPath())) {
        //                filePath = getSDCardPath();
        //            } else {
        //                // 如果是内部存储的路径，则获取内存存储的可用容量
        //                filePath = Environment.getDataDirectory().getAbsolutePath();
        //            }
        //            StatFs stat = new StatFs(filePath);
        //            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        //            return stat.getBlockSize() * availableBlocks;
        //        }

        /**
         * 获取系统存储路径
         *
         * @return
         */
        fun getRootDirectoryPath(): String {
            return Environment.getRootDirectory().absolutePath;
        }

        fun copyFile(oldPath: String, newPath: String): Boolean {

            println(SD_PIC + "<<=============DIR=========")
            val newFile: File = File(newPath)
            if (!newFile.exists()) {
                newFile.createNewFile()
            }
            try {
                var bytesum: Int = 0;
                var byteread: Int = 0;
                val oldfile = File(oldPath);
                if (oldfile.exists()) {
                    //文件存在时
                    val inStream = FileInputStream(oldPath); //读入原文件
                    val fs = FileOutputStream(newFile);
                    val buffer = ByteArray(1024)
                    while (byteread != -1) {
                        byteread = inStream.read(buffer)
                        bytesum += byteread; //字节数 文件大小
                        fs.write(buffer, 0, byteread);
                    }
//                    fs.flush()
                    inStream.close();
                    fs.close()
                    return true
                }
            } catch (e: Exception) {
                System.out.println("复制单个文件操作出错");
                e.printStackTrace();
            }
            return false

        }


    }


}