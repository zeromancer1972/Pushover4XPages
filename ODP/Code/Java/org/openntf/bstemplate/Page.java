package org.openntf.bstemplate;

import java.io.Serializable;

public class Page implements Serializable {

	private static final long serialVersionUID = -7069850227476889795L;
	private final String label;
	private final String url;
	private final String icon;
	private final boolean newWindow;
	private final boolean visible;

	public Page(final String label, final String url, final String icon, final boolean newWindow) {
		this.label = label;
		this.url = url;
		this.icon = icon;
		this.newWindow = newWindow;
		this.visible = true;
	}

	public Page(final String label, final String url, final String icon, final boolean newWindow, final boolean visible) {
		this.label = label;
		this.url = url;
		this.icon = icon;
		this.newWindow = newWindow;
		this.visible = visible;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getLabel() {
		return label;
	}

	public String getUrl() {
		return url;
	}

	public String getIcon() {
		return icon;
	}

	public boolean isNewWindow() {
		return newWindow;
	}

	public boolean isVisible() {
		return visible;
	}
}
