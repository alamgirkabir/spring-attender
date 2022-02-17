/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.sheba.device.zk;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import static org.sheba.ZkConst.USHRT_MAX;

/**
 *
 * @author kabir
 */
public class ZKPacketSerializer implements IPacketSerializer {

    @Override
    public ByteBuffer serialize(IPacket pckt) {
        byte[] bytes_command_string = pckt.getCommand().getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(8 + bytes_command_string.length).order(ByteOrder.LITTLE_ENDIAN)
                .putChar(0, (char) pckt.getCommandId()).putChar(2, (char) 0).putChar(4, (char) pckt.getSessionId())
                .putChar(6, (char) pckt.getReplyId());
        for (int i = 0; i < bytes_command_string.length; i++) {
            byte b = bytes_command_string[i];
            byteBuffer = byteBuffer.put(i + 8, b);
        }

        byteBuffer = byteBuffer.putChar(2, (char) createChkSum(byteBuffer));
        int reply_id = (pckt.getReplyId() + 1) % USHRT_MAX;
        byteBuffer = byteBuffer.putChar(6, (char) reply_id);
        return byteBuffer;
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
}
