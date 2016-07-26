package com.comm.util.generic;


import com.qiniu.util.Auth;

public final class QiniuConfig {
    //AccessKey/SecretKey
    public static final String AccessKey = "nhgpsSWCT4Smlu9OiG8MZcg7j9FMhp2OlbgeMzn2";
    public static final String SecretKey = "XqmGMDfXe7DEIyj0Ie2Kp2YHvDLiPy8-6GmCgogN";
    public static final Auth keyAuth = Auth.create(AccessKey,SecretKey);
    public static final String bucket = "allinmd";//视频存储空间
    public static final String key = "java-duke.svg";
    public static final String domain = "javasdk.qiniudn.com";
    public static final String pdfbucket = "document";//文档存储空间

    private QiniuConfig() {
    }

    public static boolean isTravis() {
        return "travis".equals(System.getenv("QINIU_TEST_ENV"));
    }
}
