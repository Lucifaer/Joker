package com.lucifaer.jokerframework.server.http;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class JokerHttpServerTest {
    @Test
    public void testInit() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        JokerHttpServer.create();
    }
}