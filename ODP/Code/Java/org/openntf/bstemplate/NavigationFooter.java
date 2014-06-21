package org.openntf.bstemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public class NavigationFooter implements Serializable {

	private static final long serialVersionUID = -8857086205056457935L;
	private final List<Page> navigation;

	public NavigationFooter() {		
		this.navigation = new ArrayList<Page>();
		

		try {
			// render if not logged in
			navigation.add(new Page("Login", "login.xsp", "", false, ExtLibUtil.getCurrentSession().getEffectiveUserName().equals(
			"Anonymous")));

			// render if logged in
			navigation.add(new Page("Logout", "/" + ExtLibUtil.getCurrentDatabase().getFilePath()
					+ "?logout&redirectto=" + "/" + ExtLibUtil.getCurrentDatabase().getFilePath(),
					"", false, !ExtLibUtil.getCurrentSession().getEffectiveUserName().equals(
							"Anonymous")));

			

		} catch (Exception e) {

		}
	}

	public List<Page> getNavigation() {
		return navigation;
	}
}
