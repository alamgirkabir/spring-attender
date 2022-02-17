/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.sheba.device.zk;

import java.nio.ByteBuffer;
import lombok.Data;

/**
 *
 * @author kabir
 */
@Data
class ZKPacket implements IPacket {

    private int sessionId;
    private int replyId;
    private int commandId;
    private String command;
    private ByteBuffer data;
    

}
