package org.sheba.udp.client;

/**
 *
 * @author kabir
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.sheba.ZkConst;
import org.sheba.udp.ZKUtil;

public class UdpServer {

//    private static final int port = 8082;

//    private static final String address = "localhost";
    private boolean running = true;

    private Logger logger = LoggerFactory.getLogger(UdpServer.class);

//    private final HashMap<String, DatagramPacket> clients = new HashMap<>();
    private DatagramSocket socket;

    public UdpServer() {
        try {
            socket = new DatagramSocket();
            new Thread(this::watchdog).start();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void startListening() {
        byte[] buffer = new byte[255];
        logger.info("UDP server started.");
        while (running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                logger.error(e.getMessage());
                continue;
            }
            onDataReceived(packet);
        }
        logger.error("UDP server stopped listening.");
    }

    private void onDataReceived(DatagramPacket packet) {
        String data = new String(packet.getData(), 0, packet.getLength());
//        logger.info(String.format("[%s] UDP packet received: %s", packet.getSocketAddress().toString(), data));

        ByteBuffer buffer = ByteBuffer.allocate(packet.getLength()).order(ByteOrder.LITTLE_ENDIAN)
                .put(packet.getData(), 0, packet.getLength());
        int session_id = Short.toUnsignedInt(buffer.getShort(4));
        int reply_id = Short.toUnsignedInt(buffer.getShort(6));

        int command = Short.toUnsignedInt(buffer.getShort(0));
        System.out.println(session_id + ", " + reply_id + ", " + command);

    }

    private void watchdog() {

        System.out.println("Watch dog");
    }

    public void send() throws IOException {
        int reply_id = -1 + ZkConst.USHRT_MAX;
        int session_id = 0;
        String command_string = "";
        ZKUtil zKUtil = new ZKUtil();
        String command = "";
        byte[] buf = zKUtil.createHeader(1000, 0, session_id, reply_id, command_string);
        
        InetSocketAddress address = new InetSocketAddress("103.197.207.10", 4370);
        DatagramPacket sendDataPack = new DatagramPacket(buf, buf.length, address);
        
        socket.send(sendDataPack);
    }
}
