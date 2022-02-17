/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.sheba.udp.config;

import org.sheba.device.zk.IPacket;
import org.sheba.device.zk.ZKCallback;


/**
 *
 * @author kabir
 */
public interface IZKDevice {
    public boolean connect(ZKCallback callback);
    public boolean disConnect(ZKCallback callback);
}
