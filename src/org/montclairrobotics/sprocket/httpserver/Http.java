package org.montclairrobotics.sprocket.httpserver;
/*
 * TO USE (in eclipse):
 * Right click on project name
 * Click Build Path > Configure Build Path
 * Select Libraries
 * Click the Down Arrow next to JRE System Library
 * Double Click "Access Rules"
 * Click Add
 * Change Resolution to Accessible
 * Use this pattern: com/sun/**
 * Click OK
 * Click OK
 * Click OK
 */

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Http implements Runnable {

  private Thread myThread;

  private HttpServer server;
  private boolean on = false;
  private int port;
  private String subDir;

  public Http(int port, String subDir) {
    this.port = port;
    this.subDir = subDir;
    myThread = new Thread(this);
    myThread.start();
  }

  public void run() {
    startServer();
  }

  public void startServer() {
    try {
      server = HttpServer.create(new InetSocketAddress(port), 0);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return;
    }
    server.createContext("/" + subDir, new MyHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
    on = true;
  }

  public void stopServer() {
    if (server != null && on) {
      server.stop(0);
      on = false;
    }
  }

  public void request(HttpExchange t) {
    try {
      Headers h = t.getResponseHeaders();
      byte[] response = getResponse(h, t);
      t.sendResponseHeaders(200, response.length);
      OutputStream os = t.getResponseBody();
      os.write(response);
      os.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Processes an http request, filling out header and returning the response
   *
   * @param h the Headers, add() each header
   * @param exchange the HttpExchange (don't have to use)
   * @return the byte[] that you want to send
   */
  public byte[] getResponse(Headers h, HttpExchange exchange) {
    return null;
  }

  class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
      request(t);
    }
  }
}
