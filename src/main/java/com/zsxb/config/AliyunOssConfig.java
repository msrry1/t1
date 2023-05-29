package com.zsxb.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Auther: csp1999
 * @Date: 2020/10/31/13:33
 * @Description: 阿里云 OSS 基本配置
 */
// 声明配置类，放入Spring容器
@Configuration
// 指定配置文件位置
@PropertySource(value = {"classpath:application-aliyun-oss.yml"})
// 指定配置文件中自定义属性前缀
@ConfigurationProperties(prefix = "aliyun")
@Data // lombok
public class AliyunOssConfig {
    @Value("${endPoint}")
    private String endPoint;// 地域节点
    @Value("${accessKeyId}")
    private String accessKeyId;
    @Value("${accessKeySecret}")
    private String accessKeySecret;
    @Value("${bucketName}")
    private String bucketName;// OSS的Bucket名称
    @Value("${urlPrefix}")
    private String urlPrefix;// Bucket 域名
    @Value("${fileHost}")
    private String fileHost;// 目标文件夹

    // 将OSS 客户端交给Spring容器托管
    @Bean
    public OSS OSSClient() {
        return new OSSClient(endPoint, accessKeyId, accessKeySecret);
    }
}
