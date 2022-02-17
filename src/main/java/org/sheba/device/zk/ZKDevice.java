/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.sheba.device.zk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.sheba.IPacketDeserializer;
import org.sheba.ZkConst;
import static org.sheba.ZkConst.*;
import org.sheba.udp.config.IZKDevice;

/**
 *
 * @author kabir
 */
public class ZKDevice implements IZKDevice, Runnable {

    private InetSocketAddress inetSocketAddress;
    private DatagramSocket clientSocket;

    private IPacketSerializer serializer;
    private IPacketDeserializer deSerializer;

    private boolean isRunning = true;
    private int sessionId;
    private int replyId;
    private Map<Integer, ZKCallback> packetMap = new HashMap<>();

    public ZKDevice(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
        try {
            this.clientSocket = new DatagramSocket();
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }

    void start() {
        new Thread(this).start();
    }

    void stop() {
        this.isRunning = false;
    }

    private byte[] getPacketData(int commandId, String command) {
        IPacket packet = new ZKPacketBuilder()
                .setSessionId(sessionId)
                .setReplyId(replyId)
                .setCommandId(commandId)
                .setCommand(command)
                .build();

        ByteBuffer buffer = new ZKPacketSerializer()
                .serialize(packet);
        return buffer.array();
    }


    @Override
    public boolean connect(ZKCallback callback) {
        this.replyId = -1 + USHRT_MAX;
        byte[] buf = getPacketData(CMD_CONNECT, StringUtils.EMPTY);
        DatagramPacket sendDataPack = new DatagramPacket(buf, buf.length, inetSocketAddress);
        try {
            packetMap.put(replyId, callback);
            clientSocket.send(sendDataPack);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean disConnect(ZKCallback callback) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        byte[] buffer = new byte[255];
        while (isRunning) {
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            try {
                this.clientSocket.receive(receivePacket);
                ByteBuffer receiveBuffer = ByteBuffer.allocate(receivePacket.getLength()).order(ByteOrder.LITTLE_ENDIAN)
                        .put(receivePacket.getData(), 0, receivePacket.getLength());

                int commandId = Short.toUnsignedInt(receiveBuffer.getShort(0));

                switch (commandId) {
                    case ZkConst.CMD_PREPARE_DATA:
                        long size = Integer.toUnsignedLong(receiveBuffer.getInt(8));
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        long bytes = size;
                        while (bytes > 0) {
                            byte[] bufRevApp = new byte[1032];
                            DatagramPacket receivePacketApp = new DatagramPacket(bufRevApp, bufRevApp.length);
                            clientSocket.receive(receivePacketApp);
                            byteArrayOutputStream.write(receivePacketApp.getData(), 8, receivePacketApp.getLength() - 8);
                            bytes -= 1024;
                        }

                        byte[] bufRevApp = new byte[8];
                        DatagramPacket receivePacketApp = new DatagramPacket(bufRevApp, bufRevApp.length);
                        clientSocket.receive(receivePacketApp);

                        byte[] bufRevAppOut = byteArrayOutputStream.toByteArray();
                        receiveBuffer = ByteBuffer
                                .allocate(bufRevAppOut.length)
                                .order(ByteOrder.LITTLE_ENDIAN)
                                .put(bufRevAppOut, 0, bufRevAppOut.length);

                        break;
                    case ZkConst.CMD_CONNECTED:
                    case ZkConst.CMD_ACK_OK:
                        break;
                    default:
                }
                IPacket rPacket = new ZKPacketDeserializer().deserialize(receiveBuffer);
                packetMap.get(replyId).onReceive(rPacket);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

}
