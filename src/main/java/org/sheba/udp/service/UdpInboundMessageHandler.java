//package org.sheba.udp.service;
//
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.integration.annotation.MessageEndpoint;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.handler.annotation.Headers;
//
//@MessageEndpoint
//public class UdpInboundMessageHandler {
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(UdpInboundMessageHandler.class);
//
//    @ServiceActivator(inputChannel = "inboundChannel")
//    public void handleMessage(Message message, @Headers Map<String, Object> headerMap) {
//        byte[] receivePacket = (byte[]) message.getPayload();
//        
//        ByteBuffer buffer = ByteBuffer.allocate(receivePacket.length).order(ByteOrder.LITTLE_ENDIAN)
//                .put(receivePacket, 0, receivePacket.length);
//        int session_id = Short.toUnsignedInt(buffer.getShort(4));
//        int reply_id = Short.toUnsignedInt(buffer.getShort(6));
//        
//        System.out.println(session_id + ", " + reply_id);
//        
//        LOGGER.info("Received UDP message: {}", new String((byte[]) message.getPayload()));
//    }
//}
