package org.example.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.io.InputStream;

public class TXCOSUtil {
    // 创建 COSClient 实例，这个实例用来后续调用请求
    // 设置用户身份信息。
    // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
    //用户的 SecretId，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
    private static final String secretId = "AKIDz350i6RqNo9MbkR9ftBFArRvIXgieTK3";
    //用户的 SecretKey，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
    private static final String secretKey = "hJsIaghYHtKdqx688A8yf8PZZP6nQr5G";
    // 设置 bucket 的地域
    // COS_REGION 请参见 https://cloud.tencent.com/document/product/436/6224
    private static final String region = "ap-beijing";
    // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
    private static final String bucketName = "big-event-1306020160";

    public static String uploadFile(String objectName, InputStream in) {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(new Region(region));
        // 设置请求协议, http 或者 https
        // 5.6.53 及更低的版本，建议设置使用 https 协议
        // 5.6.54 及更高版本，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 以下的设置，是可选的：
        // 设置 socket 读取超时，默认 30s
        clientConfig.setSocketTimeout(30 * 1000);
        // 设置建立连接超时，默认 30s
        clientConfig.setConnectionTimeout(30 * 1000);
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, in,null);
        // 设置存储类型（如有需要，不需要请忽略此行代码）, 默认是标准(Standard), 低频(standard_ia)
        // 更多存储类型请参见 https://cloud.tencent.com/document/product/436/33417
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        String url = "";
        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(putObjectResult.getRequestId());
            //url组成：https://bucket.区域/objectName
            //https://big-event-1306020160.cos.ap-beijing.myqcloud.com/001.jpg
            url = "https://" + bucketName + ".cos." + region + ".myqcloud.com/" + objectName;
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            // 确认本进程不再使用 cosClient 实例之后，关闭即可
            cosClient.shutdown();
        }
        return url;
    }
}
