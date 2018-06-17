package com.example.carlos.segundoparcial;

public class ActualizadorToken extends Thread {
    String token;

    public ActualizadorToken(String name, String token) {
        super(name);
        this.token = token;
    }

    @Override
    public synchronized void start() {
        super.start();


    }
}
