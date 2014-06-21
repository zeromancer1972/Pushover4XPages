package org.openntf.bstemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lotus.domino.NotesException;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public class Navigation implements Serializable {

	private static final long serialVersionUID = 8031965383531253276L;
	private final List<Page> navigation;

	public Navigation() {
		this.navigation = new ArrayList<Page>();
		try {
			this.navigation
					.add(new Page("Setup", "profile.xsp", "glyphicon glyphicon-wrench", false,
							ExtLibUtil.getCurrentDatabase().queryAccessRoles(
									ExtLibUtil.getCurrentSession().getEffectiveUserName()).contains(
									"[Admin]")));
		} catch (NotesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Page> getNavigation() {
		return navigation;
	}
}
