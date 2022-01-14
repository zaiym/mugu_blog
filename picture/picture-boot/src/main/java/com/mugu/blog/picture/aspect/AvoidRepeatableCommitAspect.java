package com.mugu.blog.picture.aspect;

import com.mugu.blog.core.constant.KeyConstant;
import com.mugu.blog.core.model.ResultCode;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.core.utils.IpUtils;
import com.mugu.blog.core.utils.RequestContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class AvoidRepeatableCommitAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * @param point
     */
    @Around("@annotation(com.mugu.blog.picture.aspect.AvoidRepeatableCommit)")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        HttpServletRequest request = RequestContextUtils.getRequest();

        String ip = IpUtils.getIpAddress(request);

        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        //目标类、方法
        String className = method.getDeclaringClass().getName();

        String name = method.getName();

        // 得到类名和方法
        String ipKey = String.format("%s#%s", className, name);

        // 转换成HashCode
        int hashCode = Math.abs(ipKey.hashCode());

        String key = String.format("%s:%s_%d", KeyConstant.AVOID_REPEATABLE_COMMIT, ip, hashCode);

        log.info("ipKey={},hashCode={},key={}", ipKey, hashCode, key);

        AvoidRepeatableCommit avoidRepeatableCommit = method.getAnnotation(AvoidRepeatableCommit.class);

        long timeout = avoidRepeatableCommit.timeout();

        // 设置过期时间
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, UUID.randomUUID().toString(), timeout, TimeUnit.MILLISECONDS);

        if (!Objects.requireNonNull(flag)) {
            log.info("请勿重复提交表单");
            return ResultMsg.resultFail(ResultCode.NO_REPEATABLE_SUBMIT.getCode(),ResultCode.NO_REPEATABLE_SUBMIT.getMsg());
        }

        //执行方法
        return point.proceed();
    }

}
