const request = require('request')
const API_URL = "https://api.openweathermap.org/data/2.5/";
const API_KEY = "98c74e9b4f81f7e6775d1c7bbd248367"
//const API = "https://api.openweathermap.org/data/2.5/weather?q=Buenos+Aires&appid=" + API_KEY

module.exports.extendedForecast = function (city_id, callback) {
	var url = API_URL + "forecast?id="+city_id+"&units=metric&appid="+API_KEY;

	var options = {
		url: url,
		method: 'GET',
		json: true
	}

	request(options, function (error, response) {
		if (error) {
			callback({ message: 'Unexpected error has occurred. Internal Server Error' })
		} else {
			if (response.statusCode == 200) {
				var data = response.body

				var processedData = _processData(data)

				callback(null, processedData)
			} else {
				callback({ message: 'Unexpected error has occurred.' })
			}
		}
	})
}

function _processData(data) {

	var city = {
		id: data.city.id,
		name: data.city.name,
		country: data.city.country
	}

	var list = []

	for (var i = 0; i < data.list.length; i++) {
		var item = data.list[i]
		var tmp = {
			dt_txt: item.dt_txt,
			temp_min: item.main.temp_min,
			temp_max: item.main.temp_max,
			humidity: item.main.humidity,
			image_code: item.weather[0].id,
			icon: item.weather[0].icon
		}
		list.push(tmp)
	}

	var result = {
		city: city,
		list: list
	}

	return result
}