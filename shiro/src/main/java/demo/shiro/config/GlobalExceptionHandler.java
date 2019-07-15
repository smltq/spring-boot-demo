package demo.shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    void handleException(Exception e) {
        log.error(" ==> catch a exception by handler ", e);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    void handleBusinessException(RuntimeException e) {
        log.error(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void authHandler(MissingServletRequestParameterException e) {
        log.error(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void httpMessageNotReadableHandler() {
        log.error("操作失败");
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void methodArgumentTypeMismatchHandler() {
        log.error("请检查参数格式");
    }

    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void maxUploadSizeExceededHandler() {
        log.error("上传文件过大");
    }
}
