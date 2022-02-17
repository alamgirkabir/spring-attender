//package org.sheba.udp.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;
//import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
//import org.springframework.messaging.MessageChannel;
//
//@Configuration
//public class UdpConfig {
//
//    @Value("${udp.channel}")
//    private String channel;
//    @Value("${udp.port}")
//    private Integer port;
//
//    @Bean
//    public MessageChannel inboundChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean(name = "udpReceivingAdapter")
//    public UnicastReceivingChannelAdapter udpReceivingAdapter() {
//        UnicastReceivingChannelAdapter adapter = new UnicastReceivingChannelAdapter(port);
//        System.out.println(adapter.getSocket().getInetAddress().getCanonicalHostName());
//        adapter.setOutputChannel(inboundChannel());
//        adapter.setOutputChannelName(channel);
//        return adapter;
//    }
//
//    @Bean
//    public UnicastSendingMessageHandler udpSendingAdapter() {
//        return new UnicastSendingMessageHandler("103.197.207.10", port);
////        return new UnicastSendingMessageHandler("localhost", port);
//    }
//
//}
