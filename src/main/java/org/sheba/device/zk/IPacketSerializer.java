/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.sheba.device.zk;

import java.nio.ByteBuffer;

/**
 *
 * @author kabir
 */
public interface IPacketSerializer {
    public ByteBuffer serialize(IPacket packet);
}
