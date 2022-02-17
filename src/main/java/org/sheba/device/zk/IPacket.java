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
public interface IPacket {
    public int getSessionId();
    public int getReplyId();
    public int getCommandId();
    public String getCommand();
    public ByteBuffer getData();
}
