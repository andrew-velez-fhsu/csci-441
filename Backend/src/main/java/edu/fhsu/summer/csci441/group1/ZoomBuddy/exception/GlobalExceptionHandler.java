//package edu.fhsu.summer.csci441.group1.ZoomBuddy.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//
//import java.time.LocalDateTime;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
////    @ExceptionHandler(UserNotFoundException.class)
////    public ResponseEntity<String>  handlerUserNotFoundException(UserNotFoundException exception){
//////        return new ResponseEntity<>(exception.getMessage(), HttpStatusCode.valueOf(HttpStatus.SC_NOT_FOUND));
////        return new ResponseEntity<>(exception.getMessage(), HttpStatus.SC_NOT_FOUND));
////    }
//
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ErrorDetails>  handlerUserNotFoundException(UserNotFoundException exception, WebRequest webRequest){
//        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
//                                            exception.getMessage(),
//                                            webRequest.getDescription(false),
//                                           "USER_NOT_FOUND"
//                                );
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }
//
//    // Handle Generic Exception
//    public ResponseEntity<ErrorDetails> handleGenericException(Exception exception, WebRequest webRequest){
//
//        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
//                webRequest.getDescription(false),
//                "INTERNAL_SERVER_ERROR"
//        );
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
//
//
