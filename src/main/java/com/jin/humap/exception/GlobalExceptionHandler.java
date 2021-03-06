package com.jin.humap.exception;

import com.jin.humap.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;

// @ControllerAdvice注解内部使用@ExceptionHandler、@InitBinder、@ModelAttribute注解的方法应用到所有的 @RequestMapping注解的方法
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //validation 参数
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public ApiResponse validationExceptionHandler(Exception exception) {
        BindingResult bindResult = null;
        if (exception instanceof BindException) {
            bindResult = ((BindException) exception).getBindingResult();
        } else if (exception instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException) exception).getBindingResult();
        }
        ApiResponse apiResponse = ApiResponse.ERROR(ExceptionCode.PARAM_ERROR);
        if (bindResult != null && bindResult.hasErrors()) {
            Map<String, String> errorField = new HashMap<>();
            for (FieldError error : bindResult.getFieldErrors()) {
                errorField.put(error.getField(), error.getDefaultMessage());
            }
            apiResponse.setData(errorField);
        }
        return apiResponse;
    }

    @ExceptionHandler(value = {ServletException.class})
    public ApiResponse httpRequestMethodNotSupportHandler(Exception exception) {
        return new ApiResponse(ExceptionCode.FAILED.getErrorCode(), exception.getMessage(), "");
    }


    @ExceptionHandler(value = Exception.class)
    public ApiResponse allExceptionHandler(Exception exception){
        if (exception instanceof SystemException) {
            return ApiResponse.ERROR(((SystemException) exception).getExceptionCode());
        }
        log.error("异常提示----------------------", exception);
        return ApiResponse.ERROR(ExceptionCode.FAILED);
    }

}
