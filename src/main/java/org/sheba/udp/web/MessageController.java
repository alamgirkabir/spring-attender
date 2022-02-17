package org.sheba.udp.web;

import java.net.DatagramPacket;
import org.sheba.ZkConst;
import org.sheba.device.zk.ZKDeviceManager;
import org.sheba.udp.ZKUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import org.sheba.udp.client.UdpClient;
//import org.sheba.udp.client.UdpIntegrationClient;
@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private ZKDeviceManager zKDeviceManager;

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

//    private final UdpClient udpClient;
//
//    @Autowired
//    public MessageController(UdpIntegrationClient udpClient) {
//        this.udpClient = udpClient;
//    }
    @RequestMapping(method = RequestMethod.GET)
    public void sendMessage() {
        

        zKDeviceManager.connect();

    }
}
