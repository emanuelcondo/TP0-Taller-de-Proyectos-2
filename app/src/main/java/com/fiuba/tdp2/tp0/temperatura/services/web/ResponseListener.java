package com.fiuba.tdp2.tp0.temperatura.services.web;


public interface ResponseListener {

    void onRequestCompleted(Object response);

    void onRequestError(int codError, String errorMessage);


}
