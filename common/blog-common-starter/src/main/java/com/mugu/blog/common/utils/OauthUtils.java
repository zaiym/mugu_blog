package com.mugu.blog.common.utils;

import com.mugu.blog.common.model.LoginVal;
import com.mugu.blog.common.model.RequestConstant;

public class OauthUtils {
    public static LoginVal getCurrentUser(){
        return (LoginVal) RequestContextUtils.getRequest().getAttribute(RequestConstant.LOGIN_VAL_ATTRIBUTE);
    }
}
