package com.mugu.blog.core.utils;

import com.mugu.blog.core.model.LoginVal;
import com.mugu.blog.core.model.RequestConstant;

public class OauthUtils {
    public static LoginVal getCurrentUser(){
        return (LoginVal) RequestContextUtils.getRequest().getAttribute(RequestConstant.LOGIN_VAL_ATTRIBUTE);
    }
}
