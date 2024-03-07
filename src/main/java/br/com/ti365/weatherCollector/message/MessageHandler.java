package br.com.ti365.weatherCollector.message;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.service.ResponseMessage;

@Component
public class MessageHandler {
   public ResponseEntity<Object> responseOK(JSONObject message) {
      return ResponseEntity.status(HttpStatus.OK).body(message.toString());
   }

   public ResponseEntity<Object> responseMessage(JSONObject message, HttpStatus httpStatus) {
      message.put("statusCode", httpStatus.toString());
      return ResponseEntity.status(httpStatus).body(message.toString());
   }

   @ResponseBody
   public ResponseEntity<Object> responseMessage(String message, HttpStatus httpStatus) {
      JSONObject responseJson = new JSONObject();
      responseJson.put("message", message).put("statusCode", httpStatus.toString());
      return ResponseEntity.status(httpStatus).body(responseJson.toString());
   }

   public ResponseEntity<Object> responseMessage(ResponseMessage message, HttpStatus httpStatus) {
      return ResponseEntity.status(httpStatus).body((new JSONObject(message)).toString());
   }
}