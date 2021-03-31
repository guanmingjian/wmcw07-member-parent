package cn.oreo5.service.impl;

import cn.oreo5.constant.OreoConstant;
import cn.oreo5.entity.PO.CateExample;
import cn.oreo5.entity.PO.Distance;
import cn.oreo5.entity.PO.User;
import cn.oreo5.entity.PO.UserExample;
import cn.oreo5.mapper.CateMapper;
import cn.oreo5.mapper.UserMapper;
import cn.oreo5.service.api.UserService;
import cn.oreo5.util.CompareClass;
import cn.oreo5.util.OreoUtil;
import cn.oreo5.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.*;

@Transactional(readOnly = false)
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUserLogin(String name, String password) {

        // 1.根据登录账号查询User对象
        // ①创建UserExample对象
        UserExample userExample = new UserExample();

        // ②创建Criteria对象
        UserExample.Criteria criteria = userExample.createCriteria();

        // ③在Criteria对象中封装查询条件
        criteria.andNameEqualTo(name);

        // ④调用AdminMapper的方法执行查询
        List<User> list = userMapper.selectByExample(userExample);

        // 2.判断Admin对象是否为null
        if(list == null || list.size() == 0) {
            throw new RuntimeException(OreoConstant.MESSAGE_LOGIN_FAILED);
        }

        if(list.size() > 1) {
            throw new RuntimeException(OreoConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        User user = list.get(0);

        // 3.如果User对象为null则抛出异常
        if(user == null) {
            throw new RuntimeException(OreoConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4.获取带盐加密工具
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 5.密码比对
        boolean matches = passwordEncoder.matches(password, user.getPassword());

        // 6.清除敏感信息
        user.setPassword("");

        // 7.判断是否验证通过
        if(matches==false){
            throw new RuntimeException(OreoConstant.MESSAGE_LOGIN_FAILED);
        }

        return user;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public ResultEntity<String> checkNameUnique(String name) {

        UserExample userExample = new UserExample();

        UserExample.Criteria criteria = userExample.createCriteria();

        criteria.andNameEqualTo(name);

        List<User> userList = userMapper.selectByExample(userExample);

        if(userList.size()>0){

            User user = userList.get(0);

            if(user!=null){
                return ResultEntity.failed(OreoConstant.MESSAGE_NAME_ALREADY_EXIST);
            }

            return ResultEntity.failed(OreoConstant.MESSAGE_NAME_ALREADY_EXIST);
        }

        return ResultEntity.successWithoutData();
    }

    @Override
    public String sendEmail(String email) {
        String s = null;
        try {
            s = OreoUtil.sendEmail(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public ResultEntity<String> doRegister(String name, String phoneNumber, String sex, String age, String email,String password) {

        User user = new User();
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setSex(sex);
        user.setEmail(email);

        // 4.获取带盐加密工具
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));

        // 年龄存在缺陷，用户如果输入的不是纯数字字符串，存在问题
        if (age != null && !"".equals(age)) {
            int j = Integer.parseInt(age);
            user.setAge(j);
        }

        // 存入数据
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (user.getLabel() != null){
            criteria.andLabelEqualTo(user.getLabel());
        }

        // 设置用户标签
        // 1.获取训练集
        List<User> userList = userMapper.selectByExample(userExample);
        user.setId(userList.get(userList.size()-1).getId() + 1);

        // 2.计算所有已知点到未知点的欧式距离，并根据距离对所有已知点排序
        CompareClass compare = new CompareClass();
        Set<Distance> distanceSet = new TreeSet<>(compare);
        for (User u: userList) {
            distanceSet.add(new Distance(u.getId(), user.getId(), OreoUtil.oudistance(u, user)));
        }

        // 3.选取最近的k个点
        double k = 4;

        // 4. 计算k个点所在分类出现的频率
        List<Distance> distanceList = new ArrayList<Distance>(distanceSet);
        Map<String, Integer> map = OreoUtil.getNumberOfType(distanceList, userList, k);

        Map<String, Double> p = OreoUtil.computeP(map, k);
        user.setLabel(OreoUtil.maxP(p));

        try {
            userMapper.insertSelective(user);
            return ResultEntity.successWithData(OreoConstant.MESSAGE_REGISTER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(OreoConstant.MESSAGE_REGISTER_FAILED);
        }
    }
}
