/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.sheba.udp.config;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author kabir
 */
//@ConfigurationProperties(prefix = "device.zk")
@Component
public class ZKDeviceConfig {
    /**
     * ZK host lists with ports
     */
    @Value("#{'${device.zk.hosts}'.split(',')}") 
    private final List<String> hosts = new ArrayList<>();
    public List<InetSocketAddress> getHosts() {
        List<InetSocketAddress> socketList = new ArrayList<>();
        hosts.stream().map(host -> host.split(":")).map(h -> {
            if(h.length > 1){
                String ip = h[ 0 ];
                String port = h[ 1 ];
                InetSocketAddress address = new InetSocketAddress(ip, Integer.parseInt(port));
                return address;
            }
            return null;
        }).forEachOrdered(address -> {
            if(address!= null){
                socketList.add(address);
            }
        });
        return socketList;
    }
}