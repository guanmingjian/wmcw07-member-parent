package cn.oreo5;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("cn.oreo5.mapper")
// 扫描MyBatis的Mapper接口所在的包
public class MySQLMainClass {

    public static void main(String[] args) {
        SpringApplication.run(MySQLMainClass.class, args);
    }

}
