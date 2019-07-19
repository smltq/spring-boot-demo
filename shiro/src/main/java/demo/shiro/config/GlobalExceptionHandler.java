package demo.shiro.config;

import demo.shiro.model.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    AjaxResult handleException(Exception e) {
        log.error("Exception==> catch a exception by handler ", e);
        return AjaxResult.badRequest(e.getMessage());
    }

    /**
     * 处理所有运行时异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    AjaxResult handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException==>" + e.getMessage() + "\n" + e.getStackTrace());
        return AjaxResult.badRequest(e.getMessage());
    }

    /**
     * 未授权异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    AjaxResult handleUnauthorizedException(UnauthorizedException e) {
        log.error("UnauthorizedException==>" + e.getMessage() + "\n" + e.getStackTrace());
        return AjaxResult.badRequest("未授权的访问!");
    }
}
