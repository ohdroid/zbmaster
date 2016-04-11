package com.ohdroid.zbmaster.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import com.ohdroid.zbmaster.BuildConfig;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ohdroid on 2016/4/4.
 * qiniu 工具,用于生成上传凭证,上传策略，下载凭证-----------PS:此部分应该放在服务器,所以这里是用java写的，方便移植
 */
public class QiniuUtils {

    private QiniuUtils() {

    }

    private static QiniuUtils qiniuUtils = new QiniuUtils();

    public static QiniuUtils getInstance() {
        return qiniuUtils;
    }

    /**
     * 用于测试的图片地址
     */
    public static final String IMAGE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_PICTURES;

    public static final String SERVER_IP = "http://172.18.113.246:8080";
    public static final String SERVLET = "/qiniuzbmaster/servlet/qiniu";
    /**
     * 上传token
     */
    public String uploadToken = "";

    public static final String BUCKET = "ohdroid";

    public void uploadFile() {
        final File file = new File(IMAGE_DIR);
        if (!file.exists()) {
            return;
        }

        final File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".png")) {
                    return true;
                }
                return false;
            }
        });
        if (null == files) {
            return;
        }

        System.out.println(files[0].getAbsolutePath());

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(QiniuUtils.serviceTokenPath());
        okHttpClient.newCall(requestBuilder.build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("result:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        uploadToken = response.body().string();
                        System.out.println(uploadToken + "<<======upload token");
                        //成功获取到token，执行上传

                        UploadManager uploadManager = new UploadManager();
                        uploadManager.put(files[0], null, uploadToken, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                System.out.println("result" + response.toString());
                            }
                        }, null);
                    }
                });
    }


    /**
     * 管理凭证生成
     */
    public static String getManagerAuth(String requestPath) {
        String sk = BuildConfig.QI_NIU_SECRET_KEY;
        String ak = BuildConfig.QI_NIU_ACCESS_KEY;

        String bucket = "ohdroid&prefix=image/gif";

        String token = "";
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKey secretKey = new SecretKeySpec(sk.getBytes("UTF-8"), "HmacSHA1");
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance("HmacSHA1");
            //用给定密钥初始化 Mac 对象
            mac.init(secretKey);
            //完成 Mac 操作
            byte[] text1 = mac.doFinal(requestPath.getBytes("UTF-8"));

            token = BuildConfig.QI_NIU_ACCESS_KEY + ":" + Base64.encodeToString(text1, Base64.URL_SAFE);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static String getBaseStr(String str) {
        String s = "";
        try {
            s = new String(Base64.encode(str.getBytes("UTF-8"), Base64.URL_SAFE));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String serviceTokenPath() {
        return makeUrl(SERVER_IP, SERVLET);
    }

    public static String makeUrl(String ip, String path) {
        StringBuilder sb = new StringBuilder();
        sb.append(ip);
        sb.append(path);
        return sb.toString();
    }

    public String getUploadToken() {
        return uploadToken;
    }

    public void setUploadToken(String uploadToken) {
        this.uploadToken = uploadToken;
    }

    //==========================七牛 api=======================

    /**
     * 添加七牛静态图API
     */
    public String buildQiniuApi(Map<String, String> requestParams) {
        if (requestParams == null) {
            return "";
        }

        //拼接七牛提供的API
        StringBuilder sb = new StringBuilder();
        String method = requestParams.get("method");

        if (TextUtils.isEmpty(method)) {
            return "";
        }
        sb.append("$method");
        requestParams.remove("method");


        Set<String> keys = requestParams.keySet();

        for (String key : keys) {
            sb.append("/");
            sb.append(key);
            sb.append("/");
            sb.append(requestParams.get(key));
        }

        return sb.toString();
    }
}
