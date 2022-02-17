//package org.sheba.udp.client;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetSocketAddress;
//import org.sheba.udp.ZKUtil;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UdpSimpleClient implements UdpClient {
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(UdpSimpleClient.class);
//
//    @Value("${udp.port}")
//    private Integer port;
//
//    public void sendMessage(String message) {
//        LOGGER.info("Sending UDP message: {}", message);
//        InetSocketAddress sock = new InetSocketAddress("103.197.207.10", port);
//
//        ZKUtil zKUtil = new ZKUtil();
//        byte[] udpMessage = message.getBytes();
//        DatagramPacket packet = null;
//        try (DatagramSocket socket = new DatagramSocket()) {
////            packet = new DatagramPacket(udpMessage, udpMessage.length, sock);
////            socket.send(packet);
//            zKUtil.executeCmd(socket, sock, 0, message, null);
//            socket.close();
//        } catch (IOException e) {
//            LOGGER.error(e.getMessage(), e);
//        }
//        LOGGER.info("Sending UDP message was successful");
//    }
//}
