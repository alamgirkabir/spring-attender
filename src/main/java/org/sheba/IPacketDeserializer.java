/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.sheba;

import org.sheba.device.zk.IPacket;

/**
 *
 * @author kabir
 */
public interface IPacketDeserializer {
    public IPacket deserialize();
}
