package top.jiangyixin.lite.conf.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.jiangyixin.lite.conf.web.domain.User;
import top.jiangyixin.lite.conf.web.mapper.UserMapper;
import top.jiangyixin.lite.conf.web.service.UserService;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/19 下午3:33
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
}
