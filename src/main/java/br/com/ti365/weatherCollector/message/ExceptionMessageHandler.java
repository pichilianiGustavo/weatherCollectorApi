package br.com.ti365.weatherCollector.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionMessageHandler extends ResponseEntityExceptionHandler {
	private static final Logger log = LogManager.getLogger();

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> exceptionHandler(Exception e) {
		MessageHandler messageHandler = new MessageHandler();
		log.debug(e);
		return messageHandler.responseMessage("Erro não mapeado, entre em contato com o Suporte Técnico!",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}