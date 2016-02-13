package com.github.inventorywatcher;

public class PortProvider {
    private PortProvider(){};

    private static int port = 1050;

    public static synchronized int getNextPort(){
        port++;
        return port;
    }
}
