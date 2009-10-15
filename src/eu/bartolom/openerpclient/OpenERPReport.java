package eu.bartolom.openerpclient;

import java.util.AbstractMap;
import java.util.HashMap;

/**
 *
 * @author bfreitas
 */
public class OpenERPReport {

	private OpenERPClient client;

	public OpenERPReport(OpenERPClient client) throws Exception {
		this.client = client;
		this.client.setDomain("report");
	}

	public String getPdfReport(AbstractMap map) throws Exception {
		map.put("report_type", "pdf");
		try {
			Object[] params = new Object[]{client.getDbName(), client.getUid(), client.getPassword(),
				map.get("model"), new Object[]{map.get("id")}, map};
			Object res = client.report(params);
			// get report
			boolean done = false;
			String rep64 = null;
			while (!done) {
				params = new Object[]{client.getDbName(), client.getUid(), client.getPassword(), res};
				Object nres = client.report_get(params);
				if (nres instanceof HashMap) {
					HashMap m = (HashMap) nres;
					String actualState = m.get("state").toString();
					if (!actualState.equals("false")) {
						done = true;
						rep64 = m.get("result").toString();
					}
					Thread.sleep(700);
				}
			}
			return rep64;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;

		}
	}
}
