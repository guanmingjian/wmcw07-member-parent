package cn.oreo5.service.api;

import cn.oreo5.entity.PO.User;
import cn.oreo5.util.ResultEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserService {

    /**
     * 检测登录
     * @param name
     * @param password
     * @return
     */
    User checkUserLogin(String name, String password);

    /**
     * 通过id查用户
     * @param id
     * @return
     */
    User getUserById(Integer id);

    /**
     * 验证用户名是否存在
     * @param name
     * @return
     */
    ResultEntity<String> checkNameUnique(String name);

    /**
     * 发送邮件
     * @param email
     * @return
     */
    String sendEmail(String email);

    /**
     * 注册用户
     * @param name
     * @param phoneNumber
     * @param sex
     * @param age
     * @param email
     * @param password
     * @return
     */
    ResultEntity<String> doRegister(String name, String phoneNumber, String sex, String age, String email,String password);
}
