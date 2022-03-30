package com.zyaud.idata.iam.biz.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 分块上传工具类
 * 记录文件签名
 */
public class UploadUtils {
    /**
     * 内部类记录分块上传文件信息
     */
    private static class Value {
        String name;
        boolean[] status;

        Value(int n) {
            this.name = generateFileName();
            this.status = new boolean[n];
        }
    }

    private static Map<String, Value> chunkMap = new HashMap<>();

    /**
     * 判断文件所有分块是否已上传
     *
     * @param key
     * @return
     */
    public static boolean isUploaded(String key) {
        if (isExist(key)) {
            for (boolean b : chunkMap.get(key).status) {
                if (!b) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否有分块已上传
     *
     * @param key
     * @return
     */
    private static boolean isExist(String key) {
        return chunkMap.containsKey(key);
    }

    /**
     * 为文件添加上传分块记录
     *
     * @param key
     * @param chunk
     */
    public static void addChunk(String key, int chunk) {
        chunkMap.get(key).status[chunk] = true;
    }

    /**
     * 从map中删除键为key的键值对
     *
     * @param key
     */
    public static void removeKey(String key) {
        if (isExist(key)) {
            chunkMap.remove(key);
        }
    }

    /**
     * 获取随机生成的文件名
     *
     * @param key
     * @param chunks
     * @return
     */
    public static String getFileName(String key, int chunks) {
        if (!isExist(key)) {
            synchronized (UploadUtils.class) {
                if (!isExist(key)) {
                    chunkMap.put(key, new Value(chunks));
                }
            }
        }
        return chunkMap.get(key).name;
    }


    /**
     * 写入文件
     *
     * @param target
     * @param src
     * @throws IOException
     */
    public static void write(String target, InputStream src) throws IOException {
        OutputStream os = new FileOutputStream(target);
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            os.write(buf, 0, len);
        }
        os.flush();
        os.close();
    }

    /**
     * 分块写入文件
     *
     * @param target
     * @param targetSize
     * @param src
     * @param srcSize
     * @param chunks
     * @param chunk
     * @throws IOException
     */
    public static void writeWithBlok(String target, Long targetSize, InputStream src, Long srcSize, Integer chunks, Integer chunk) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(target, "rw");
        randomAccessFile.setLength(targetSize);
        if (chunk == chunks - 1) {
            randomAccessFile.seek(targetSize - srcSize);
        } else {
            randomAccessFile.seek(chunk * srcSize);
        }
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            randomAccessFile.write(buf, 0, len);
        }
        randomAccessFile.close();
    }

    /**
     * 生成随机文件名
     *
     * @return
     */
    public static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 创建文件
     *
     * @param path
     * @return
     */
    public static File createFile(String path) {
        if (path == null) {
            return null;
        } else {
            File file = new File(path);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            return new File(path);
        }
    }
}
