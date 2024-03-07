package br.com.ti365.weatherCollector.message;

public class ResponseMessage {
	   private int statusCode;
	   private String body;

	   public ResponseMessage() {
	   }

	   public ResponseMessage(int statusCode, String body) {
	      this.statusCode = statusCode;
	      this.body = body;
	   }

	   public int getStatusCode() {
	      return this.statusCode;
	   }

	   public void setStatusCode(int statusCode) {
	      this.statusCode = statusCode;
	   }

	   public String getBody() {
	      return this.body;
	   }

	   public void setBody(String body) {
	      this.body = body;
	   }
	}
