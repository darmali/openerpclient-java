package eu.bartolom.openerpclient;

import java.util.AbstractMap;
import java.util.HashMap;

/**
 *
 * @author bfreitas
 */
public class OpenERPWizard {

	private OpenERPClient client;
	private Integer currentWizardId = null;

	public OpenERPWizard(OpenERPClient client) throws Exception {
		this.client = client;
		this.client.setDomain("wizard");
	}

	public Integer createWizard(String name) throws Exception {
		Object[] params = new Object[]{client.getDbName(), client.getUid(), client.getPassword(), name,};
		Object result = client.execute("create", params);
		if (result instanceof Integer) {
			this.setCurrentWizardId((Integer) result);
			return getCurrentWizardId();
		}
		throw new Exception("Error creating wizard for '" + name + "'");
	}

	public AbstractMap getForm(String model, Integer id) throws Exception {
		HashMap modelMap = new HashMap();
		modelMap.put("model", model);
		modelMap.put("id", id);
		Object[] params = new Object[]{
			client.getDbName(), client.getUid(), client.getPassword(),
			getCurrentWizardId(), modelMap};
		Object result = client.execute("execute", params);
		if (result instanceof AbstractMap) {
			return (AbstractMap) result;
		} else {
			throw new Exception("Error 1 - getting form: model = '" + model + "', id = '" + id + "'");
		}
	}

	public void submitForm(String model, Integer id, AbstractMap formMap) {
		HashMap modelMap = new HashMap();
		modelMap.put("model", model);
		modelMap.put("id", id);
		modelMap.put("form", formMap);
		Object[] params = new Object[]{
			client.getDbName(), client.getUid(), client.getPassword(),
			getCurrentWizardId(), modelMap, "split"};
		try {
			client.execute("execute", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @return the currentWizardId
	 */
	public Integer getCurrentWizardId() {
		return currentWizardId;
	}

	/**
	 * @param currentWizardId the currentWizardId to set
	 */
	public void setCurrentWizardId(Integer currentWizardId) {
		this.currentWizardId = currentWizardId;
	}
}
