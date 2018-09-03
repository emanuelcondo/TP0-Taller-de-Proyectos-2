const express = require('express')
const CityService = require('./services/city.service')
const WeatherService = require('./services/weather.service')

const app = express()

app.get('/api/cities', function (req, res) {

	console.log('GET /api/cities'+(req.query.search ? ('?search='+req.query.search) : ''))

	CityService.search(req.query.search, function (err, result) {
		var data = {
			cities: result
		}
		res.status(200).json(data)
	});
})

app.get('/api/weather/forecast/:city_id', function (req, res) {

	console.log('GET /api/weather/forecast/'+req.params.city_id)

	WeatherService.extendedForecast(req.params.city_id, function (error, result) {
		if (error) {
			res.status(500).json(error)
		} else {
			res.status(200).json(result)
		}
	});
})

app.listen(3000, () => console.log('App Server listening on port 3000!'))