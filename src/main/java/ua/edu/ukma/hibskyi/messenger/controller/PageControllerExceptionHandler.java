package ua.edu.ukma.hibskyi.messenger.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ua.edu.ukma.hibskyi.messenger.exception.BaseException;

@ControllerAdvice(basePackages = "ua.edu.ukma.hibskyi.messenger.controller.web")
public class PageControllerExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public String handleBaseException(BaseException ex, Model model) {
        model.addAttribute("status", ex.getStatus());
        model.addAttribute("message", ex.getMessage());
        return "errors/error";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFoundException(NoResourceFoundException ex, Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("message", "Resource not found: " + ex.getMessage());
        return "errors/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("message", "Unexpected exception: " + ex.getMessage());
        return "errors/error";
    }
}
