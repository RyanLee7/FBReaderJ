package org.zlibrary.core.library;

import java.io.InputStream;

import java.util.*;
import org.zlibrary.core.util.*;

import org.zlibrary.core.application.ZLApplication;
import org.zlibrary.core.xml.*;
import org.zlibrary.core.view.ZLPaintContext;

public abstract class ZLibrary {
	private final HashMap myProperties = new HashMap();

	public static ZLibrary getInstance() {
		return ourImplementation;
	}
	
	private static ZLibrary ourImplementation;

	protected ZLibrary() {
		ourImplementation = this;
	}

	public final String getApplicationName() {
		return (String)myProperties.get("applicationName");
	}

	protected final Class getApplicationClass() {
		try {
			Class clazz = Class.forName((String)myProperties.get("applicationClass"));
			if ((clazz != null) && ZLApplication.class.isAssignableFrom(clazz)) {
				return clazz;
			}
		} catch (ClassNotFoundException e) {
		}
		return null;
	}

	abstract public ZLPaintContext createPaintContext();
	abstract public InputStream getResourceInputStream(String fileName);
	abstract public void openInBrowser(String reference);

	protected final void loadProperties() {
		new ZLXMLReaderAdapter() {
			public void startElementHandler(String tag, ZLStringMap attributes) {
				if (tag.equals("property")) {
					myProperties.put(attributes.getValue("name"), attributes.getValue("value"));
				}
			}
		}.read("data/application.xml");
	}
}
