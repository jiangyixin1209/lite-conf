package top.jiangyixin.lite.conf.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/19 下午3:28
 */
@MapperScan("top.jiangyixin.lite.conf.web.mapper")
@SpringBootApplication
public class LiteConfApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiteConfApplication.class, args);
    }
}
