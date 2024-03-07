package br.com.ti365.weatherCollector.service.implementations;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.ti365.weatherCollector.service.interfaces.ApiConsumer;
import br.com.ti365.weatherCollector.service.interfaces.CepApiConsumer;
import br.com.ti365.weatherCollector.service.interfaces.WeatherApiConsumer;

@Service
public class WeatherApiConsumerImpl implements WeatherApiConsumer {

	private static final Logger log = LogManager.getLogger();
	@Autowired
	private CepApiConsumer cepApiConsumer;
	@Value("${url.weather.api}")
	private String urlWeatherApi;
	@Autowired
	private ApiConsumer apiConsumer;

	private JSONObject getWeatherInformationByLatAndLong(String latitude, String longitude, UUID uuid) throws Exception {
		log.info(uuid + " | Buscando informacoes de clima para as coordenadas --> Latitude: [" + latitude
				+ "] Longitude: [" + longitude + "]");
		JSONObject responseJson = new JSONObject();
		log.info(uuid + " | URL da API de clima: "+ urlWeatherApi + "latitude=" + latitude + "&longitude=" + longitude
				+ "&current=temperature_2m,wind_speed_10m");
		responseJson = apiConsumer.getData(urlWeatherApi + "latitude=" + latitude + "&longitude=" + longitude
				+ "&current=temperature_2m,wind_speed_10m");
		log.info(uuid + " | Retorno da requisicao: " + responseJson);
		return responseJson;
	}

	@Override
	public JSONObject getWeatherByCep(String cep, UUID uuid) throws Exception {
		log.info(uuid + " | Buscando coordenadas atraves do CEP: " + cep);
		JSONObject auxJson = cepApiConsumer.getLatitudeAndLongitudeByCep(cep, uuid);
		if(auxJson.has("ERRO")) {
			return auxJson;
		} else {
		JSONObject responseJson = getWeatherInformationByLatAndLong(
				auxJson.get("lat").toString(),
				auxJson.get("lng").toString(), uuid);
		return responseJson;
		}
	}

}
