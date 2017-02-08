package com.tunyun.product.ebc.web.impl;

import java.io.IOException;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.common.system.utils.thrift.AbstractThriftClientCreator;
import com.tunyun.product.ebc.web.idl.DataServer;
import com.tunyun.product.ebc.web.idl.DataServer.Iface;

public class RuleThriftClient<T extends Iface> extends AbstractThriftClientCreator<T> {

	/**
	 * 发布服务时端口，默认端口为8998
	 */
	private int port = 8998;
	/**
	 * 主机名，默认为localhost
	 */
	private String hostname = "localhost";
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T createThriftClient(TProtocol protocol) {
		return (T) new DataServer.Client(protocol);
	}

	@Override
	protected TTransport buildTTransport() throws IOException {
		return new TSocket(hostname, port);
	}

}
