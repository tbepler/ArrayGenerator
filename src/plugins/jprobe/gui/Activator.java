package plugins.jprobe.gui;

import java.awt.GraphicsEnvironment;
import java.awt.Point;

import jprobe.services.ErrorHandler;
import jprobe.services.JProbeCore;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import plugins.jprobe.gui.services.GUIErrorHandler;
import plugins.jprobe.gui.services.JProbeGUI;

public class Activator implements BundleActivator{
	
	private JProbeCore core;
	private ErrorHandler errorHandler;
	private JProbeGUIFrame gui;
	private ServiceRegistration<JProbeGUI> register = null;
	
	@Override
	public void start(BundleContext context) throws Exception {
		ServiceReference ref = context.getServiceReference(JProbeCore.class);
		core = (JProbeCore) context.getService(ref);
		gui = new JProbeGUIFrame(core, "JProbe", context.getBundle());
		errorHandler = new GUIErrorHandler(gui);
		context.registerService(ErrorHandler.class, errorHandler, null);
		gui.setVisible(true);
		register = context.registerService(JProbeGUI.class, gui, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if(register != null){
			register.unregister();
		}
		gui.dispose();
	}

}