package com.wuzf.swing.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class MessageSourceHandler {
    //    https://blog.csdn.net/P397226804/article/details/103960592
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    /**
     * 如果是根据Request请求的语言来决定国际化：
     * @param messageKey
     * @return
     */
    public String getMessage(String messageKey) {
        String message = messageSource.getMessage(messageKey, null, RequestContextUtils.getLocale(request));
        return message;
    }

    /**
     * 如果是根据应用部署的服务器系统来决定国际化：
     * @param messageKey
     * @return
     */
    public String getMessageServer(String messageKey) {
        String message = messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
        return message;
    }

}
