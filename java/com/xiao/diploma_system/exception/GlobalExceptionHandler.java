package com.xiao.diploma_system.exception;//package com.wust.supermarket.exception;
//
//import com.wust.supermarket.domain.Response;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
///**
// * 全局异常处理
// */
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    /**
//     * 处理404
//     *
//     * @return 响应信息
//     */
//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public Response requestHandlingNoHandlerFound() {
//        return new Response<>(404, "404 Not found", null);
//    }
//
//    /**
//     * 处理405
//     *
//     * @return 响应信息
//     */
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
//    public Response requestHandlingMethodNotSupported() {
//        return new Response<>(405, "Request Method Not Allowed", null);
//    }
//
//
//    /**
//     * 处理500
//     *
//     * @param e 服务器内部错误
//     * @return 响应信息
//     */
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public Response handleException(Exception e) {
//        return new Response<>(500, "Internal Server Error", e.getMessage());
//    }
//
//
//    //TODO
//    // BAD_REQUEST(400, "Bad Request")
//    // UNAUTHORIZED(401, "Unauthorized")
//}
