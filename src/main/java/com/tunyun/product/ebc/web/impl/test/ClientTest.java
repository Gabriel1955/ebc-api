package com.tunyun.product.ebc.web.impl.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TTransportFactory;

import com.tunyun.product.ebc.web.idl.DataServer;
import com.tunyun.product.ebc.web.impl.RuleThriftClient;

public class ClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RuleThriftClient<DataServer.Iface> client = new RuleThriftClient<DataServer.Iface>();
			client.setTprotocolFactory(new TBinaryProtocol.Factory());
			client.setTransportFactory(new TTransportFactory());
			client.setTargetInterface(DataServer.Iface.class);
			client.setHostname("127.0.0.1");
			client.setPort(8999);
			client.afterPropertiesSet();

			Properties p = new Properties();
			InputStream ins = new FileInputStream(new File("/home/op/process/ebc/ebcapi/conf/env.properties"));
			p.load(ins);
			String model = p.getProperty("test.model");
			String item = p.getProperty("test.item");
			String param = p.getProperty("test.param");
			System.out.println("model==========" + model);
			System.out.println("item==========" + item);
			System.out.println("param==========" + param);
			
			DataServer.Iface clientIface = (DataServer.Iface) client.getObject();
			String str = clientIface.execute(model, item, param);
			System.out.println("client  receive==========" + str);
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
