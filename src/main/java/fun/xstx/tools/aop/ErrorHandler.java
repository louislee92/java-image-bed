package fun.xstx.tools.aop;

import fun.xstx.tools.model.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * 错误拦截器
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {


    /**
     * 参数为实体类
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultVO handleValidException(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        return ResultVO.fail(objectError.getDefaultMessage());
    }

    /**
     * 参数为单个参数或多个参数
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultVO handleConstraintViolationException(ConstraintViolationException e) {
        // 从异常对象中拿到ObjectError对象
        return ResultVO.fail(e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()).get(0));
    }

    /**
     * 未知数据库异常拦截
     * @param e
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public ResultVO defaultException(SQLException e) {
        log.error("数据库出错: ", e);
        return ResultVO.fail("数据库出错："  + e.getMessage());
    }

    /**
     * 已知异常拦截
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultVO runtimeException(RuntimeException e) {
        if(e instanceof NullPointerException) {
            log.error("空指针异常：", e);
        } else if(e instanceof IndexOutOfBoundsException) {
            log.error("数组下标越界: ", e);
        } else if(isKnownException(e)) {
            log.error("已知异常：{}", e.getMessage());
        } else {
            log.error("未知异常", e);
        }
        String res = e.getMessage() == null ? "系统后台报错" : e.getMessage();
        return ResultVO.fail(e.getMessage());
    }

    private boolean isKnownException(Exception e) {
        String canonicalName = e.getClass().getCanonicalName();
        return "java.lang.RuntimeException".equals(canonicalName)
                || "java.lang.IllegalStateException".equals(canonicalName);
    }

    /**
     * 未知异常拦截
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultVO defaultException(Exception e) {
        log.error("Error Handler: ", e);
        return ResultVO.fail("未知错误："  + e.getMessage());
    }
}
