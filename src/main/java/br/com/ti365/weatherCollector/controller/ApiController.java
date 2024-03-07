package br.com.ti365.weatherCollector.controller;

import java.net.Inet4Address;
import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ti365.weatherCollector.message.ExceptionMessageHandler;
import br.com.ti365.weatherCollector.message.MessageHandler;
import br.com.ti365.weatherCollector.service.interfaces.WeatherApiConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = { "/api/v1" }, produces = { "application/json" })
@Api(tags = { "Api Controller" }, description = "")
@CrossOrigin
public class ApiController {
	private static final Logger log = LogManager.getLogger();
	@Autowired
	private ExceptionMessageHandler exceptionHandler;
	@Autowired
	private WeatherApiConsumer weatherApiConsumer;
	@Value("${api.key}")
	private String apiKey;
	private MessageHandler messageHandler = new MessageHandler();

	@CrossOrigin
	@ApiResponses({
			@ApiResponse(code = 200, message = "{\"message\": \"Requisição realizada com sucesso!\", \"statusCode\": 200}"),
			@ApiResponse(code = 400, message = "{\"message\": \"Não foi localizado nenhum serviço disponível\", \"statusCode\": 400}"),
			@ApiResponse(code = 401, message = "{\"message\": \"Invalid key\", \"statusCode\": 401}"),
			@ApiResponse(code = 500, message = "{\"message\": \"Erro interno de servidor! Por gentileza, verifique se sua requisição está no formato correto\", \"statusCode\": 500}") })
	@GetMapping(value = { "/getWeatherData" }, produces = { "application/json" })
	public ResponseEntity<Object> getWeatherData(@RequestHeader("api-key") String apiKey,
			@RequestParam(name = "cep") String cep) throws Exception {
		UUID uuid = UUID.randomUUID();
		JSONObject responseJson = new JSONObject();
		responseJson.put("Date", LocalDateTime.now());
		responseJson.put("uuid", uuid);
		log.info(uuid + " | Nova requisicao realizada! GET para método getWeatherData! Parametro informado: " + cep);
		log.info(uuid + " | IP de Origem da requisicao: " + Inet4Address.getLocalHost().getHostAddress());
		if (!this.validateApiKey(apiKey)) {
			log.error(uuid + " | Chave inválida: " + apiKey);
			responseJson.put("message", "Chave inválida");
			return this.messageHandler.responseMessage(responseJson, HttpStatus.UNAUTHORIZED);
		} else {
			try {
				responseJson.put("API Response", weatherApiConsumer.getWeatherByCep(cep, uuid));
				return this.messageHandler.responseMessage(responseJson, HttpStatus.OK);
			} catch (Exception e) {
				log.error(uuid + " | ERROR! Exception! " + e.getLocalizedMessage());
				return this.messageHandler.responseMessage(responseJson, HttpStatus.OK);
			}
			
		}
	}

	private Boolean validateApiKey(String apiKey) {
		return this.apiKey.equals(apiKey);
	}
}
