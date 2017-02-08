package com.tunyun.product.ebc.web.impl;

import java.net.InetSocketAddress;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.common.system.utils.thrift.AbstractThriftServerCreator;
import com.tunyun.product.ebc.web.idl.DataServer;
import com.tunyun.product.ebc.web.idl.DataServer.Iface;

public class RuleThritServer<T extends Iface> extends AbstractThriftServerCreator<T> implements InitializingBean {

	// log
	private Logger log = LoggerFactory.getLogger(RuleThritServer.class);

	/**
	 * 初始化
	 */
	public void init() {
		log.info("初始化thrift端口监听.......................................");
		this.setTprotocolFactory(new TBinaryProtocol.Factory());
		this.setTransportFactory(new TTransportFactory());
		this.start();
	}

	@Override
	public TProcessor getTProcessor() {
		return new DataServer.Processor<T>(getServiceObject());
	}

	/**
	 * 创建TServer的服务对象，请在子类中继承实现
	 * 
	 * @return 创建TServer的实例对象
	 */
	@Override
	public TServer getTServer() {
		try {
			TServerTransport serverTransport = buildTServerTransport();
			// TThreadedSelectorServer.Args arg = new
			// TThreadedSelectorServer.Args((TNonblockingServerSocket)serverTransport);
			TThreadPoolServer.Args arg = new TThreadPoolServer.Args(serverTransport);
			arg.processor(getTProcessor());
			arg.protocolFactory(getTprotocolFactory());
			arg.transportFactory(getTtransportFactory());
			arg.maxWorkerThreads(100);
			arg.minWorkerThreads(10);
			TServer server = new TThreadPoolServer(arg);
			return server;
		} catch (TTransportException e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	@Override
	protected TServerTransport buildTServerTransport() throws TTransportException {
		return new TServerSocket(
				new InetSocketAddress(getHostPortServiceName().getHostname(), getHostPortServiceName().getPort()));
		// return new TNonblockingServerSocket(new
		// InetSocketAddress(getHostPortServiceName().getHostname(),getHostPortServiceName().getPort()));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// System.out.println("====================start
		// thrift======================================");
		// 启动
		// init();
	}

}
