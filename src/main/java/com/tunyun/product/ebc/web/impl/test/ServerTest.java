package com.tunyun.product.ebc.web.impl.test;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportFactory;

import com.common.system.utils.thrift.HostPortServiceName;
import com.tunyun.product.ebc.web.impl.IRuleFace;
import com.tunyun.product.ebc.web.impl.RuleThritServer;

public class ServerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RuleThritServer s = new RuleThritServer();
		HostPortServiceName hostPortServiceName = new HostPortServiceName();
		hostPortServiceName.setHostname("127.0.0.1");
		hostPortServiceName.setPort(8999);
		hostPortServiceName.setServiceName("test");
		s.setHostPortServiceName(hostPortServiceName);
		s.setServiceObject(new IRuleFace());
		s.setTprotocolFactory(new TBinaryProtocol.Factory());
		s.setTransportFactory(new TTransportFactory());
		s.start();
	}

}
