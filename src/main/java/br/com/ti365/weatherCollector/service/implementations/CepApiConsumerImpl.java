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

@Service
public class CepApiConsumerImpl implements CepApiConsumer {

	@Value("${url.cep.api}")
	private String urlCepApi;
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private ApiConsumer apiConsumer;

	@Override
	public JSONObject getLatitudeAndLongitudeByCep(String cep, UUID uuid) throws Exception {
		JSONObject responseJson = new JSONObject();
		JSONObject auxJson = apiConsumer.getData(urlCepApi + cep);
		log.info(uuid + " | Chamando API do CEP para obter coordenadas! URL da API: " + urlCepApi);
		if (auxJson.has("code")) {
			log.error(uuid + " | ERRO na requisicao! Por favor, revise os parametros enviados! Código resultante da requisição: " + auxJson.get("code"));
			String str = (String) auxJson.get("code");
			switch (str) {
			case "invalid": 
				responseJson.put("ERRO", auxJson.get("message"));
			case "not_found": 
				responseJson.put("ERRO", auxJson.get("message"));
				break;
			default: 
				throw new IllegalArgumentException("Unexpected value: " + auxJson.get("message"));
			}
		} else {
			responseJson.put("lat", auxJson.get("lat"));
			responseJson.put("lng", auxJson.get("lng"));
			log.info(uuid + " | 200OK - Coordenadas Obtidas --> Latitude: [" + responseJson.get("lat") + "] Longitude: [" + responseJson.getDouble("lng") + "]");
		}
		return responseJson;
	}
}
