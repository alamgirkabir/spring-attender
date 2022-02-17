/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.sheba.udp.client;

import java.io.IOException;
import org.springframework.stereotype.Service;

/**
 *
 * @author kabir
 */
@Service
public class WxServer {

    private static UdpServer udpServer;

    public static void initializeServer() throws IOException {
        udpServer = new UdpServer();
        new Thread(() -> udpServer.startListening()).start();
        udpServer.send();
    }
}
