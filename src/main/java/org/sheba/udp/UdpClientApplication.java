package org.sheba.udp;

import java.io.IOException;
import javax.annotation.PostConstruct;
import org.sheba.device.zk.ZKDeviceManager;
import org.sheba.udp.client.WxServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.integration.config.EnableIntegration;

@ComponentScan("org.sheba")
@SpringBootApplication
public class UdpClientApplication {

    @Autowired
    private ZKDeviceManager zKDeviceManager;
    
    public static void main(String[] args) throws IOException {
        SpringApplication.run(UdpClientApplication.class, args);
//        WxServer.initializeServer();
    }
    
    @PostConstruct
    public void start(){
        zKDeviceManager.start();
    }
}
