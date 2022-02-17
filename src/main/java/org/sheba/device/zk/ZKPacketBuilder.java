/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.sheba.device.zk;

/**
 *
 * @author kabir
 */
public class ZKPacketBuilder {
    private int sessionId;
    private int replyId;
    private int commandId;
    private String command;

    public ZKPacketBuilder setReplyId(Integer replyId) {
        this.replyId = replyId;
        return this;
    }

    public ZKPacketBuilder setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public ZKPacketBuilder setCommandId(Integer commandId) {
        this.commandId = commandId;
        return this;
    }

    public ZKPacketBuilder setCommand(String command) {
        this.command = command;
        return this;
    }
    
    public IPacket build(){
        ZKPacket packet = new ZKPacket();
        packet.setSessionId(sessionId);
        packet.setCommand(command);
        packet.setCommandId(commandId);
        packet.setReplyId(replyId);
        return packet;
    }
}
