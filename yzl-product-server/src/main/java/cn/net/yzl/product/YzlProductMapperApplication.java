package cn.net.yzl.product;

import cn.net.yzl.common.swagger2.EnableSwagger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@MapperScan("cn.net.yzl.product.dao")
@SpringBootApplication(scanBasePackages = {"cn.net.yzl.product", "cn.net.yzl.logger"})
@EnableSwagger
public class YzlProductMapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(YzlProductMapperApplication.class, args);
    }

}
