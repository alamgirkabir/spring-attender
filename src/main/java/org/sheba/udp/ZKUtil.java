/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.sheba.udp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.sheba.ZkCallBack;
import static org.sheba.ZkConst.CMD_ACK_OK;
import static org.sheba.ZkConst.CMD_CONNECT;
import static org.sheba.ZkConst.CMD_CONNECTED;
import static org.sheba.ZkConst.CMD_PREPARE_DATA;
import static org.sheba.ZkConst.USHRT_MAX;

/**
 *
 * @author kabir
 */
public class ZKUtil {

    private int session_id = 0;
    private int reply_id = -1 + USHRT_MAX;

    public <T> T executeCmd(DatagramSocket clientSocket, InetSocketAddress address,  int command, String command_string, ZkCallBack<T> zkCallBack) throws IOException {
        if (command == CMD_CONNECT) {
            this.reply_id = -1 + USHRT_MAX;
        }

        byte[] buf = createHeader(command, 0, session_id, reply_id, command_string);

        DatagramPacket sendDataPack = new DatagramPacket(buf, buf.length, address);

        clientSocket.send(sendDataPack);
        byte[] bufRev = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(bufRev, bufRev.length);
        clientSocket.receive(receivePacket);

        ByteBuffer buffer = ByteBuffer.allocate(receivePacket.getLength()).order(ByteOrder.LITTLE_ENDIAN)
                .put(receivePacket.getData(), 0, receivePacket.getLength());
        session_id = Short.toUnsignedInt(buffer.getShort(4));
        reply_id = Short.toUnsignedInt(buffer.getShort(6));

        int comBack = checkValid(buffer);
        if (CMD_ACK_OK == comBack || CMD_CONNECTED == comBack) {
            return zkCallBack.callBack(true, buffer);
        } else if (CMD_PREPARE_DATA == comBack) {
            long size = Integer.toUnsignedLong(buffer.getInt(8));
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
            return zkCallBack.callBack(true, ByteBuffer.allocate(bufRevAppOut.length).order(ByteOrder.LITTLE_ENDIAN).put(bufRevAppOut, 0, bufRevAppOut.length));
        } else {
            return zkCallBack.callBack(false, buffer);
        }
    }

    private int checkValid(ByteBuffer buffer) {
        int command = Short.toUnsignedInt(buffer.getShort(0));
        return command;
    }

    private int createChkSum(ByteBuffer p) {

        int chksum = 0;

        for (int i = 0; i < p.remaining(); i += 2) {
            if (i == p.remaining() - 1) {
                chksum += p.get(i);
            } else {
                chksum += p.getChar(i);
            }
            chksum %= USHRT_MAX;
        }

        chksum = USHRT_MAX - chksum - 1;

        return chksum;
    }

    public byte[] createHeader(int command, int chksum, int session_id, int reply_id, String command_string) {
        byte[] bytes_command_string = command_string.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(8 + bytes_command_string.length).order(ByteOrder.LITTLE_ENDIAN)
                .putChar(0, (char) command).putChar(2, (char) chksum).putChar(4, (char) session_id)
                .putChar(6, (char) reply_id);
        for (int i = 0; i < bytes_command_string.length; i++) {
            byte b = bytes_command_string[i];
            byteBuffer = byteBuffer.put(i + 8, b);
        }

        byteBuffer = byteBuffer.putChar(2, (char) createChkSum(byteBuffer));
        reply_id = (reply_id + 1) % USHRT_MAX;
        byteBuffer = byteBuffer.putChar(6, (char) reply_id);
        return byteBuffer.array();
    }

}
