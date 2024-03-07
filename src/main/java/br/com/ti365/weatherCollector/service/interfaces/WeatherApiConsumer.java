package br.com.ti365.weatherCollector.service.interfaces;

import java.util.UUID;

import org.json.JSONObject;

public interface WeatherApiConsumer {

	JSONObject getWeatherByCep(String cep, UUID uuid) throws Exception;
	
}
