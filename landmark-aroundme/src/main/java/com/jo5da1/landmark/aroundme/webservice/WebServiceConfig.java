package com.jo5da1.landmark.aroundme.webservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;

@Configuration(proxyBeanMethods = false)
public class WebServiceConfig {

  private static final String NAMESPACE_URI = "http://jo5da1.com/location-based-landmark-aroundme";

  @Bean
  public DefaultWsdl11Definition landmarksAroundMe(SimpleXsdSchema landmarksAroundMeWs) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("landmarksAroundMe");
    wsdl11Definition.setLocationUri("/services");
    wsdl11Definition.setTargetNamespace("http://jo5da1.com/location-based-landmark-aroundme");
    wsdl11Definition.setSchema(landmarksAroundMeWs);
    return wsdl11Definition;
  }
}
