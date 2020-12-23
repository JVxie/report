package com.jvxie.report;


import com.jvxie.report.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

//@Slf4j
public class UnderlineToCamelArgumentResolver extends AbstractCustomizeResolver {

    /**
     * Whether the given {@linkplain MethodParameter method parameter} is
     * supported by this resolver.
     *
     * @param parameter the method parameter to check
     * @return {@code true} if this resolver supports the supplied parameter;
     * {@code false} otherwise
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(com.jvxie.report.ParameterModel.class);
    }

    /**
     * 装载参数
     *
     * @param methodParameter       方法参数
     * @param modelAndViewContainer 返回视图容器
     * @param nativeWebRequest      本次请求对象
     * @param webDataBinderFactory  数据绑定工厂
     * @return the resolved argument value, or {@code null}
     * @throws Exception in case of errors with the preparation of argument values
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Object org = handleParameterNames(methodParameter, nativeWebRequest);
        valid(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory, org);
        return org;
    }

    //处理参数
    private Object handleParameterNames(MethodParameter parameter, NativeWebRequest webRequest) throws ParseException {
//        log.info("test123");
        Object obj = BeanUtils.instantiate(parameter.getParameterType());
//        log.info("test1243");
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        Iterator<String> paramNames = webRequest.getParameterNames();
        while (paramNames.hasNext()) {
            String paramName = paramNames.next();
            Object o = webRequest.getParameter(paramName);
            String paramNameToCamel = StringUtil.underLineToCamel(paramName);
            // 特判日期格式
            PropertyDescriptor propertyDescriptor = wrapper.getPropertyDescriptor(paramNameToCamel);
            if (o != null && propertyDescriptor.getPropertyType().equals(Date.class)) {
                if (!StringUtils.isEmpty(o)) {
                    String format = "yyyy-MM-dd HH:mm:ss";
                    if (((String) o).length() <= 10) {
                        format = "yyyy-MM-dd";
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);//注意月份是MM
                    o = simpleDateFormat.parse((String) o);
                } else {
                    o = null;
                }
            }
            // 绑定参数
            wrapper.setPropertyValue(paramNameToCamel, o);
        }
        return obj;
    }
}

