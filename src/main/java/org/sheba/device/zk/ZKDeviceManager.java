/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.sheba.device.zk;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import org.sheba.udp.config.ZKDeviceConfig;
import org.springframework.stereotype.Service;

/**
 *
 * @author kabir
 */
@Service
public class ZKDeviceManager {

    private ZKDeviceConfig zKDeviceConfig;
    private final List<ZKDevice> devices;
    
    public ZKDeviceManager(ZKDeviceConfig zKDeviceConfig) {
        this.zKDeviceConfig = zKDeviceConfig;
        devices = new ArrayList<>();
        for (InetSocketAddress host : zKDeviceConfig.getHosts()) {
            ZKDevice zKDevice = new ZKDevice(host);
            devices.add(zKDevice);
        }
    }
    
    public void start(){
        for (ZKDevice device : devices) {
            device.start();
        }
    }
    public void connect(){
        for (ZKDevice device : devices) {
            device.connect(new ZKCallback(){
                @Override
                public void onReceive(IPacket packet) {
                    System.out.println(packet.getCommand());
                }
                
            });
            System.out.println("Connected");
        }
    }
    public void stop(){
        for (ZKDevice device : devices) {
            device.stop();
        }
    }
}
