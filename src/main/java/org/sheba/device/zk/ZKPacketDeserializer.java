/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.sheba.device.zk;

import java.nio.ByteBuffer;

/**
 *
 * @author kabir
 */
public class ZKPacketDeserializer implements IPacketDeserializer{

    @Override
    public IPacket deserialize(ByteBuffer buffer) {

        int sessionId = Short.toUnsignedInt(buffer.getShort(4));
        int replyId = Short.toUnsignedInt(buffer.getShort(6));

        int commandId = getCommandId(buffer);
        
        IPacket iPacket = new ZKPacketBuilder()
                .setCommandId(commandId)
                .setCommand("")
                .setReplyId(replyId)
                .setSessionId(sessionId)
                .build();
        return iPacket;
    }
    
    
    private int getCommandId(ByteBuffer buffer) {
        int command = Short.toUnsignedInt(buffer.getShort(0));
        return command;
    }

}
