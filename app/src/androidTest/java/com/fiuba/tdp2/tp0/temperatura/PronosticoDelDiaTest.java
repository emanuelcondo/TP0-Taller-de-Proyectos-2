package com.fiuba.tdp2.tp0.temperatura;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.fiuba.tdp2.tp0.temperatura.dominio.Pronostico;
import com.fiuba.tdp2.tp0.temperatura.dominio.PronosticoDelDia;
import com.fiuba.tdp2.tp0.temperatura.dominio.PronosticoFactory;
import org.json.JSONException;
import org.json.JSONObject ;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Vector;

@RunWith(AndroidJUnit4.class)
public class PronosticoDelDiaTest {

    private static String climaActualTest = "{\"coord\":{\"lon\":145.77,\"lat\":-16.92},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"base\":\"stations\",\"main\":{\"temp\":10,\"pressure\":1013,\"humidity\":78,\"temp_min\":24,\"temp_max\":24},\"visibility\":10000,\"wind\":{\"speed\":3.1,\"deg\":10},\"clouds\":{\"all\":20},\"dt\":1535875200,\"sys\":{\"type\":1,\"id\":8166,\"message\":0.0018,\"country\":\"AU\",\"sunrise\":1535833375,\"sunset\":1535875835},\"id\":2172797,\"name\":\"Cairns\",\"cod\":200}";

    private static String clima5DiasTest = "{\"cod\":\"200\",\"message\":0.0019,\"cnt\":40,\"list\":[{\"dt\":1535878800,\"main\":{\"temp\":22.19,\"temp_min\":22.19,\"temp_max\":22.89,\"pressure\":1013.62,\"sea_level\":1027.89,\"grnd_level\":1013.62,\"humidity\":98,\"temp_kf\":-0.71},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.01,\"deg\":98.0054},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-02 09:00:00\"},{\"dt\":1535889600,\"main\":{\"temp\":21.19,\"temp_min\":21.19,\"temp_max\":21.72,\"pressure\":1015.09,\"sea_level\":1029.48,\"grnd_level\":1015.09,\"humidity\":100,\"temp_kf\":-0.53},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.36,\"deg\":116.5},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-02 12:00:00\"},{\"dt\":1535900400,\"main\":{\"temp\":20.4,\"temp_min\":20.4,\"temp_max\":20.75,\"pressure\":1014.65,\"sea_level\":1029.15,\"grnd_level\":1014.65,\"humidity\":100,\"temp_kf\":-0.35},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.18,\"deg\":130.501},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-02 15:00:00\"},{\"dt\":1535911200,\"main\":{\"temp\":20.11,\"temp_min\":20.11,\"temp_max\":20.29,\"pressure\":1013.98,\"sea_level\":1028.5,\"grnd_level\":1013.98,\"humidity\":100,\"temp_kf\":-0.18},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.66,\"deg\":151.501},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-02 18:00:00\"},{\"dt\":1535922000,\"main\":{\"temp\":20.9,\"temp_min\":20.9,\"temp_max\":20.9,\"pressure\":1015.39,\"sea_level\":1029.9,\"grnd_level\":1015.39,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.92,\"deg\":162.501},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-02 21:00:00\"},{\"dt\":1535932800,\"main\":{\"temp\":24.49,\"temp_min\":24.49,\"temp_max\":24.49,\"pressure\":1016.4,\"sea_level\":1030.79,\"grnd_level\":1016.4,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":5.82,\"deg\":154.009},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-03 00:00:00\"},{\"dt\":1535943600,\"main\":{\"temp\":25.77,\"temp_min\":25.77,\"temp_max\":25.77,\"pressure\":1014.44,\"sea_level\":1028.73,\"grnd_level\":1014.44,\"humidity\":75,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":5.86,\"deg\":140.504},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-03 03:00:00\"},{\"dt\":1535954400,\"main\":{\"temp\":25.14,\"temp_min\":25.14,\"temp_max\":25.14,\"pressure\":1013.42,\"sea_level\":1027.64,\"grnd_level\":1013.42,\"humidity\":80,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":5.56,\"deg\":126.002},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-03 06:00:00\"},{\"dt\":1535965200,\"main\":{\"temp\":22.79,\"temp_min\":22.79,\"temp_max\":22.79,\"pressure\":1014.38,\"sea_level\":1028.75,\"grnd_level\":1014.38,\"humidity\":92,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.91,\"deg\":121.501},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-03 09:00:00\"},{\"dt\":1535976000,\"main\":{\"temp\":21.57,\"temp_min\":21.57,\"temp_max\":21.57,\"pressure\":1015.88,\"sea_level\":1030.34,\"grnd_level\":1015.88,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.12,\"deg\":135.003},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-03 12:00:00\"},{\"dt\":1535986800,\"main\":{\"temp\":20.39,\"temp_min\":20.39,\"temp_max\":20.39,\"pressure\":1015.24,\"sea_level\":1029.63,\"grnd_level\":1015.24,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.01,\"deg\":155},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-03 15:00:00\"},{\"dt\":1535997600,\"main\":{\"temp\":19.51,\"temp_min\":19.51,\"temp_max\":19.51,\"pressure\":1014.1,\"sea_level\":1028.65,\"grnd_level\":1014.1,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.67,\"deg\":175.003},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-03 18:00:00\"},{\"dt\":1536008400,\"main\":{\"temp\":19.45,\"temp_min\":19.45,\"temp_max\":19.45,\"pressure\":1015.02,\"sea_level\":1029.54,\"grnd_level\":1015.02,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.92,\"deg\":151.509},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-03 21:00:00\"},{\"dt\":1536019200,\"main\":{\"temp\":23.62,\"temp_min\":23.62,\"temp_max\":23.62,\"pressure\":1016.19,\"sea_level\":1030.53,\"grnd_level\":1016.19,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.98,\"deg\":122.504},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-04 00:00:00\"},{\"dt\":1536030000,\"main\":{\"temp\":23.96,\"temp_min\":23.96,\"temp_max\":23.96,\"pressure\":1014.72,\"sea_level\":1029.02,\"grnd_level\":1014.72,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.67,\"deg\":115.503},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-04 03:00:00\"},{\"dt\":1536040800,\"main\":{\"temp\":23.41,\"temp_min\":23.41,\"temp_max\":23.41,\"pressure\":1014,\"sea_level\":1028.21,\"grnd_level\":1014,\"humidity\":92,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02d\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":4.57,\"deg\":117.001},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-04 06:00:00\"},{\"dt\":1536051600,\"main\":{\"temp\":22.07,\"temp_min\":22.07,\"temp_max\":22.07,\"pressure\":1015.02,\"sea_level\":1029.37,\"grnd_level\":1015.02,\"humidity\":99,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":4.31,\"deg\":125.506},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-04 09:00:00\"},{\"dt\":1536062400,\"main\":{\"temp\":21.59,\"temp_min\":21.59,\"temp_max\":21.59,\"pressure\":1015.86,\"sea_level\":1030.32,\"grnd_level\":1015.86,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":4.56,\"deg\":124.006},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-04 12:00:00\"},{\"dt\":1536073200,\"main\":{\"temp\":21.41,\"temp_min\":21.41,\"temp_max\":21.41,\"pressure\":1015.13,\"sea_level\":1029.54,\"grnd_level\":1015.13,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":48},\"wind\":{\"speed\":4.48,\"deg\":119.505},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-04 15:00:00\"},{\"dt\":1536084000,\"main\":{\"temp\":21.22,\"temp_min\":21.22,\"temp_max\":21.22,\"pressure\":1014.12,\"sea_level\":1028.58,\"grnd_level\":1014.12,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":4.46,\"deg\":120.502},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-04 18:00:00\"},{\"dt\":1536094800,\"main\":{\"temp\":21.41,\"temp_min\":21.41,\"temp_max\":21.41,\"pressure\":1015.05,\"sea_level\":1029.6,\"grnd_level\":1015.05,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":4.51,\"deg\":123.003},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-04 21:00:00\"},{\"dt\":1536105600,\"main\":{\"temp\":22.56,\"temp_min\":22.56,\"temp_max\":22.56,\"pressure\":1016.04,\"sea_level\":1030.54,\"grnd_level\":1016.04,\"humidity\":96,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":4.76,\"deg\":114.501},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-05 00:00:00\"},{\"dt\":1536116400,\"main\":{\"temp\":22.2,\"temp_min\":22.2,\"temp_max\":22.2,\"pressure\":1014.56,\"sea_level\":1028.9,\"grnd_level\":1014.56,\"humidity\":99,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":4.22,\"deg\":116.505},\"rain\":{\"3h\":0.06},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-05 03:00:00\"},{\"dt\":1536127200,\"main\":{\"temp\":21.66,\"temp_min\":21.66,\"temp_max\":21.66,\"pressure\":1013.59,\"sea_level\":1027.83,\"grnd_level\":1013.59,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":88},\"wind\":{\"speed\":4.11,\"deg\":130.504},\"rain\":{\"3h\":0.08},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-05 06:00:00\"},{\"dt\":1536138000,\"main\":{\"temp\":20.92,\"temp_min\":20.92,\"temp_max\":20.92,\"pressure\":1014.64,\"sea_level\":1028.96,\"grnd_level\":1014.64,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":3.76,\"deg\":142},\"rain\":{\"3h\":0.15},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-05 09:00:00\"},{\"dt\":1536148800,\"main\":{\"temp\":21.2,\"temp_min\":21.2,\"temp_max\":21.2,\"pressure\":1015.75,\"sea_level\":1030.12,\"grnd_level\":1015.75,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":36},\"wind\":{\"speed\":3.86,\"deg\":137.502},\"rain\":{\"3h\":0.05},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-05 12:00:00\"},{\"dt\":1536159600,\"main\":{\"temp\":20.7,\"temp_min\":20.7,\"temp_max\":20.7,\"pressure\":1014.96,\"sea_level\":1029.39,\"grnd_level\":1014.96,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.67,\"deg\":120.5},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-05 15:00:00\"},{\"dt\":1536170400,\"main\":{\"temp\":20.39,\"temp_min\":20.39,\"temp_max\":20.39,\"pressure\":1014,\"sea_level\":1028.48,\"grnd_level\":1014,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.86,\"deg\":116.502},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-05 18:00:00\"},{\"dt\":1536181200,\"main\":{\"temp\":20.4,\"temp_min\":20.4,\"temp_max\":20.4,\"pressure\":1015.09,\"sea_level\":1029.63,\"grnd_level\":1015.09,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.78,\"deg\":118.502},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-05 21:00:00\"},{\"dt\":1536192000,\"main\":{\"temp\":23.34,\"temp_min\":23.34,\"temp_max\":23.34,\"pressure\":1015.99,\"sea_level\":1030.29,\"grnd_level\":1015.99,\"humidity\":92,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.12,\"deg\":112.505},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-06 00:00:00\"},{\"dt\":1536202800,\"main\":{\"temp\":23.89,\"temp_min\":23.89,\"temp_max\":23.89,\"pressure\":1013.98,\"sea_level\":1028.22,\"grnd_level\":1013.98,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.55,\"deg\":106.502},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-06 03:00:00\"},{\"dt\":1536213600,\"main\":{\"temp\":23.36,\"temp_min\":23.36,\"temp_max\":23.36,\"pressure\":1012.78,\"sea_level\":1026.94,\"grnd_level\":1012.78,\"humidity\":91,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.48,\"deg\":113.504},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-06 06:00:00\"},{\"dt\":1536224400,\"main\":{\"temp\":21.97,\"temp_min\":21.97,\"temp_max\":21.97,\"pressure\":1013.76,\"sea_level\":1028.12,\"grnd_level\":1013.76,\"humidity\":99,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.27,\"deg\":125.5},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-06 09:00:00\"},{\"dt\":1536235200,\"main\":{\"temp\":21.14,\"temp_min\":21.14,\"temp_max\":21.14,\"pressure\":1015.19,\"sea_level\":1029.55,\"grnd_level\":1015.19,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.42,\"deg\":133.001},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-06 12:00:00\"},{\"dt\":1536246000,\"main\":{\"temp\":20.36,\"temp_min\":20.36,\"temp_max\":20.36,\"pressure\":1014.61,\"sea_level\":1029.13,\"grnd_level\":1014.61,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.65,\"deg\":140},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-06 15:00:00\"},{\"dt\":1536256800,\"main\":{\"temp\":20.02,\"temp_min\":20.02,\"temp_max\":20.02,\"pressure\":1013.99,\"sea_level\":1028.46,\"grnd_level\":1013.99,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":5.37,\"deg\":144.501},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-09-06 18:00:00\"},{\"dt\":1536267600,\"main\":{\"temp\":20.28,\"temp_min\":20.28,\"temp_max\":20.28,\"pressure\":1015.27,\"sea_level\":1029.85,\"grnd_level\":1015.27,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":5.57,\"deg\":136.002},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-06 21:00:00\"},{\"dt\":1536278400,\"main\":{\"temp\":22.35,\"temp_min\":22.35,\"temp_max\":22.35,\"pressure\":1016.47,\"sea_level\":1030.95,\"grnd_level\":1016.47,\"humidity\":96,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":6.01,\"deg\":120.001},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-07 00:00:00\"},{\"dt\":1536289200,\"main\":{\"temp\":23.28,\"temp_min\":23.28,\"temp_max\":23.28,\"pressure\":1014.87,\"sea_level\":1029.15,\"grnd_level\":1014.87,\"humidity\":90,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":5.98,\"deg\":115.002},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-07 03:00:00\"},{\"dt\":1536300000,\"main\":{\"temp\":23.14,\"temp_min\":23.14,\"temp_max\":23.14,\"pressure\":1013.65,\"sea_level\":1027.96,\"grnd_level\":1013.65,\"humidity\":92,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":5.75,\"deg\":120.001},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-09-07 06:00:00\"}],\"city\":{\"id\":2172797,\"name\":\"Cairns\",\"coord\":{\"lat\":-16.9167,\"lon\":145.7667},\"country\":\"AU\"}}";

    @Test
    public void parsingTest5Dias() {
        try {
            JSONObject json = new JSONObject(clima5DiasTest);
            Vector<PronosticoDelDia> pronostico = PronosticoFactory.deJSON(json);
            assertEquals(pronostico.get(0).tmpPromedioDia,  21.5, 1);
            assertEquals(pronostico.get(0).tmpPromedioNoche, 21.5, 1);
            assertEquals(pronostico.get(0).nombreImagenDia, "01d");
            assertEquals(pronostico.get(0).nombreImagenNoche, "01n");

        }
        catch (JSONException e) {
            assertFalse(true);
        }


        assertEquals(4, 2 + 2);
    }

    @Test
    public void parsingTest5DiasYActual() {
        try {
            JSONObject json5 = new JSONObject(clima5DiasTest);
            JSONObject jsonActual = new JSONObject(climaActualTest);
            Vector<PronosticoDelDia> pronostico = PronosticoFactory.deJSON(json5, jsonActual);
            assertEquals(pronostico.get(0).tmpPromedioDia,  15.5, 1);
            assertEquals(pronostico.get(0).tmpPromedioNoche, 21.5, 1);
            assertEquals(pronostico.get(0).nombreImagenDia, "01d");
            assertEquals(pronostico.get(0).nombreImagenNoche, "01n");

        }
        catch (JSONException e) {
            assertFalse(true);
        }


        assertEquals(4, 2 + 2);
    }


}
