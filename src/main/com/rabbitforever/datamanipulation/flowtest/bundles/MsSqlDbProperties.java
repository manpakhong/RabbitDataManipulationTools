package com.rabbitforever.datamanipulation.flowtest.bundles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsSqlDbProperties extends PropertiesBase implements DbProperties{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private final static String FILE_NAME = "mssql.db.properties";
	private String connectString;
	private String host;
	private String port;
	private String userName;
	private String password;
	private String schema;
	private String systemSchema;
	private String classForName;
	
	public MsSqlDbProperties() throws Exception {
		super(FILE_NAME);
	}
	
	public String getSchema() {
		schema = this.getPropValues("schema");
		return schema;
	}

	public String getConnectString() {
		connectString = this.getPropValues("connection_string");
		return connectString;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		host = this.getPropValues("host");
		return host;
	}

	public String getPort() {
		port = this.getPropValues("port");
		return port;
	}

	public String getUserName() {
		userName = this.getPropValues("username");
		return userName;
	}

	public String getPassword() {
		password = this.getPropValues("password");
		return password;
	}

	public String getClassForName() {
		classForName = this.getPropValues("class_for_name");
		return classForName;
	}

	public String getSystemSchema() {
		systemSchema = this.getPropValues("system_schema");
		return systemSchema;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MsSqlDbProperties [logger=");
		builder.append(logger);
		builder.append(", className=");
		builder.append(className);
		builder.append(", connectString=");
		builder.append(connectString);
		builder.append(", host=");
		builder.append(host);
		builder.append(", port=");
		builder.append(port);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", schema=");
		builder.append(schema);
		builder.append(", systemSchema=");
		builder.append(systemSchema);
		builder.append(", classForName=");
		builder.append(classForName);
		builder.append("]");
		return builder.toString();
	}
	
}
