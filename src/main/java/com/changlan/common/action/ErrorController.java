//package com.changlan.common.action;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class ErrorController {
//
//    @Resource
//    private HttpServletRequest request;
//
//    /**
//     * 重新抛出异常
//     * @throws Exception 
//     */
//    @RequestMapping("/error/rethrow")
//    public void rethrow() throws Exception { 
//        throw  (Exception)request.getAttribute("error");
//    }
//}
//
