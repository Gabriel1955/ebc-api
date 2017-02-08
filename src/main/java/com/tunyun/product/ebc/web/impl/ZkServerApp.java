package com.tunyun.product.ebc.web.impl;

import com.common.zk.cluster.mo.SimpleServiceInstanceDetailMO;
import com.common.zk.cluster.publish.ZkClusterServicePublisher;
import com.google.common.collect.Maps;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * <BR>Copyright (c) 2015, AlexYan All Rights Reserved.<BR>
 *
 * @author yanhaijian
 * @version v1.0@20150409
 * @since v1.0
 */
public class ZkServerApp {
    private static final Logger LOG = LoggerFactory.getLogger(ZkServerApp.class);

    private ZkClusterServicePublisher publisher = null;
    
    //参数
    private String hostname;
    private int port;
    private String name;

    public void setPublisher(ZkClusterServicePublisher publisher) {
        this.publisher = publisher;
    }

    public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void start() throws Exception {
        SimpleServiceInstanceDetailMO instanceDetailMO = new SimpleServiceInstanceDetailMO(1.0F);
        UriSpec uriSpec = new UriSpec("scheme://{address}:{port}");
//        uriSpec.add(new UriSpec.Part("thrift", false));
        Map map = Maps.newHashMap();
        map.put("thrift", false);
        uriSpec.build(map);

        ServiceInstance<SimpleServiceInstanceDetailMO> instance1 = ServiceInstance.<SimpleServiceInstanceDetailMO>builder()
                .name(name)
                .port(port)
                .address(hostname)   //address不写的话，会取本地ip
                .payload(new SimpleServiceInstanceDetailMO(1F))
                .uriSpec(uriSpec)
                .build();
        LOG.info("instance:id={},uri={}", new Object[]{instance1.getId(), instance1.buildUriSpec(map)});
        publisher.registerService(instance1);
    }
}
