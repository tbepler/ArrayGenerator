package plugins.testDataAndFunction;

import jprobe.services.Debug;
import jprobe.services.JProbeCore;
import jprobe.services.Log;
import jprobe.services.data.DataReader;
import jprobe.services.data.DataWriter;
import jprobe.services.function.Function;
import jprobe.services.function.FunctionPrototype;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator{
	
	private JProbeCore core;
	private FunctionPrototype fun = new TestFunctionPrototype();
	private FunctionPrototype longFun = null;
	private FunctionPrototype dataParamFun = new DataParamFunctionPrototype();
	private FunctionPrototype fieldParamFun = new FieldParamFunctionPrototype();
	private FunctionPrototype dataFieldParamFun = new DataFieldFunctionPrototype();
	private DataReader testReader = new TestDataReader();
	private DataWriter testWriter = new TestDataWriter();
	
	@Override
	public void start(BundleContext context) throws Exception {
		longFun = new LongFunctionPrototype(context.getBundle());
		ServiceReference ref = context.getServiceReference(JProbeCore.class);
		core = (JProbeCore) context.getService(ref);
		core.getFunctionManager().addFunctionPrototype(fun, context.getBundle());
		core.getFunctionManager().addFunctionPrototype(longFun, context.getBundle());
		core.getFunctionManager().addFunctionPrototype(dataParamFun, context.getBundle());
		core.getFunctionManager().addFunctionPrototype(fieldParamFun, context.getBundle());
		core.getFunctionManager().addFunctionPrototype(dataFieldParamFun, context.getBundle());
		core.getDataManager().addDataReader(testReader, context.getBundle());
		core.getDataManager().addDataWriter(testWriter, context.getBundle());
		if(Debug.getLevel() == Debug.FULL || Debug.getLevel() == Debug.LOG){
			Log.getInstance().write(context.getBundle(), "TestPlugin started.");
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		core.getFunctionManager().removeFunctionPrototype(fun, context.getBundle());
		core.getFunctionManager().removeFunctionPrototype(longFun, context.getBundle());
		core.getFunctionManager().removeFunctionPrototype(dataParamFun, context.getBundle());
		core.getFunctionManager().removeFunctionPrototype(fieldParamFun, context.getBundle());
		core.getFunctionManager().removeFunctionPrototype(dataFieldParamFun, context.getBundle());
		core.getDataManager().removeDataReader(testReader, context.getBundle());
		core.getDataManager().removeDataWriter(testWriter, context.getBundle());
		if(Debug.getLevel() == Debug.FULL || Debug.getLevel() == Debug.LOG){
			Log.getInstance().write(context.getBundle(), "TestPlugin stopped.");
		}
	}

}
