package com.github.xzb617.encryption.autoconfigure.consoles;

import com.github.xzb617.encryption.autoconfigure.consoles.details.ConsoleDetails;
import com.github.xzb617.encryption.autoconfigure.consoles.details.DecryptedDetail;
import com.github.xzb617.encryption.autoconfigure.consoles.details.EncryptedDetail;
import com.github.xzb617.encryption.autoconfigure.envirs.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

/**
 * 框架的上下文拦截器
 * @author xzb617
 * @date 2022/5/10 12:36
 * @description:
 */
public class EncryptionContextInterceptor implements HandlerInterceptor {

    private final static Logger LOGGER = LoggerFactory.getLogger(EncryptionContextInterceptor.class);

    private final Configurator configurator;

    public EncryptionContextInterceptor(Configurator configurator) {
        this.configurator = configurator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求编号
        String requestId = request.getHeader(this.configurator.getRequestIdKey());
        if (StringUtils.isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        String requestUri = request.getRequestURI();
        // 获取请求时间
        String requestTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        // 初始化 ContextHolder
        EncryptionContextHolder.set(new ConsoleDetails(requestId, requestUri, requestTime, new HashSet<DecryptedDetail>(), new HashSet<EncryptedDetail>()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ConsoleDetails consoleDetails = EncryptionContextHolder.get();
        // 打印请求日志
        LOGGER.info(consoleDetails.mergeResult());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 必须清理 ContextHolder
        EncryptionContextHolder.remove();
    }
}
