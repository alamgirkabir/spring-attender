//package org.sheba.udp.client;
//
//import static org.sheba.ZkConst.CMD_CONNECT;
//import static org.sheba.ZkConst.USHRT_MAX;
//import org.sheba.udp.ZKUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
//import org.springframework.integration.support.MessageBuilder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UdpIntegrationClient implements UdpClient {
//    private final static Logger LOGGER = LoggerFactory.getLogger(UdpIntegrationClient.class);
//    private final UnicastSendingMessageHandler udpSendingAdapter;
//    
//    @Autowired
//    public UdpIntegrationClient(UnicastSendingMessageHandler udpSendingAdapter) {
//        this.udpSendingAdapter = udpSendingAdapter;
//    }
//    public void sendMessage(String message) {
//        LOGGER.info("Sending UDP message: {}", message);
//        
//        int reply_id = -1 + USHRT_MAX;
//        int session_id = 0;
//        String command_string = "";
//        ZKUtil zKUtil = new ZKUtil();
//        String command = "";
//        byte[] buf = zKUtil.createHeader(1000, 0, session_id, reply_id, command_string);
//        
//        udpSendingAdapter.handleMessage(MessageBuilder.withPayload(buf).build());
//    }
//}
