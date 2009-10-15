package eu.bartolom.openerpclient;

import java.net.URL;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 *
 * @author bfreitas
 */
public class OpenERPClient extends XmlRpcClient {

	private String server;
	private String dbName;
	private String username;
	private String password;
	private Integer uid;
	private XmlRpcClientConfigImpl config;

	public OpenERPClient(String server, String dbName,
			String username, String password) throws Exception {
		this.server = server;
		this.dbName = dbName;
		this.username = username;
		this.password = password;
		config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(getServer() + "common"));
		setConfig(config);
		Object[] params = new Object[]{getDbName(), getUsername(), getPassword()};
		Object res = login(params);
		if (!res.toString().equals("false")) {
			uid = Integer.parseInt(res.toString());
		}
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * @return the username
	 */
	protected String getUsername() {
		return username;
	}

	/**
	 * @return the uid
	 */
	protected Integer getUid() {
		return uid;
	}

	/**
	 * @return the password
	 */
	protected String getPassword() {
		return password;
	}

	protected void setDomain(String domain) throws Exception {
		config.setServerURL(new URL(server + domain));
	}

	public Object execute(Object[] params) throws Exception {
		return execute("execute", params);
	}

	private Object login(Object[] params) throws Exception {
		return execute("login", params);
	}

	public Object report(Object[] params) throws Exception {
		return execute("report", params);
	}

	public Object report_get(Object[] params) throws Exception {
		return execute("report_get", params);
	}
}
