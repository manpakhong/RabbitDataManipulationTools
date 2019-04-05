package com.rabbitforever.datamanipulation.flowtest.bundles;

public interface DbProperties {
	public String getSchema();
	public String getConnectString();
	public String getHost();
	public String getPort();
	public String getUserName();
	public String getPassword();
	public String getClassForName();
	public String getSystemSchema();
}
