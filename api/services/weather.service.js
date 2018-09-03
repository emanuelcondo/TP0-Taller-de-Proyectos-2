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

	/*
	{
		nombreDia: string
		imagenDia: int
		imagenNoche: int
		temperaturaDia: float
		temperaturaNoche: float
		hayDataDelDia: boolean

	}
	*/

	var currentDay = -1, currentMonth = -1, currentYear = -1
	var dayTemp = 0, nightTemp = 0
	var dayImage = 0, nightImage = 0
	var dayAVGCount = 0
	var nightAVGCount = 0
	var forecastDaysCount = 0
	var DAYS = [ 'HOY', 'MAÑANA' ]
	var WEEKDAYS = [ 'DOMINGO', 'LUNES', 'MARTES', 'MIÉRCOLES', 'JUEVES', 'VIERNES', 'SÁBADO' ]

	var forecast = []

	for (var i = 0; i < list.length; i ++) {
		var aux = {}

		var item = list[i]
		var day = parseInt(item.dt_txt.substring(8,10))
		var month = parseInt(item.dt_txt.substring(5,7))
		var year = parseInt(item.dt_txt.substring(0,4))
		var hour = parseInt(item.dt_txt.substring(11,13))

		if (day != currentDay) {
			if (nightAVGCount != 0) {
				if (forecastDaysCount == 0 || forecastDaysCount == 1) {
					aux.nombreDia = DAYS[forecastDaysCount]
				} else {
					var today = new Date()
					aux.nombreDia = WEEKDAYS[today.getDay()]
				}

				if (dayAVGCount == 0) {
					aux.hayDataDelDia = false
				} else {
					aux.temperaturaDia = (dayTemp / dayAVGCount)
				}

				aux.temperaturaNoche = (nightTemp / nightAVGCount)
				aux.imagenDia = dayImage
				aux.imagenNoche = nightImage
				forecast.push(aux)
				dayTemp = 0
				nightTemp = 0
				dayAVGCount = 0
				nightAVGCount = 0
				forecastDaysCount += 1
			}

			currentDay = day
			currentMonth = month
			currentYear = year
		}

		if (hour >= 6 && hour < 20) {
			dayTemp += ((item.temp_max + item.temp_min) / 2)
			dayImage = item.image_code
			dayAVGCount += 1
		} else {
			nightTemp += ((item.temp_max + item.temp_min) / 2)
			nightImage = item.image_code
			nightAVGCount += 1
		}
	}

	var today = new Date()
	var last = {
		nombreDia: WEEKDAYS[today.getDay()],
		imagenDia: dayImage,
		imagenNoche: nightImage,
		temperaturaDia: (dayTemp / dayAVGCount),
		temperaturaNoche: (nightTemp / nightAVGCount),
		hayDataDelDia: true
	}

	var result = {
		city: city,
		list: forecast
	}

	return result
}