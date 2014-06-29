package jprobe.services.function;

import util.progress.ProgressListener;
import jprobe.JProbeActivator;
import jprobe.services.ErrorHandler;
import jprobe.services.Workspace;
import jprobe.services.function.components.NullComponent;

/**
 * This class represents a flag argument. Flag arguments are boolean arguments that are always optional.
 * The default value should be false. If the flag is set, the the {@link #process(P, boolean)} method will
 * be called with a boolean value of true. If the flag is not set, then the method will not be called.
 * 
 * @author Tristan Bepler
 *
 * @param <P> - The parameter class used by this argument.
 */

public abstract class FlagArgument<P> extends AbstractArgument<P, NullComponent>{

	protected FlagArgument(String name, String tooltip, String category,
			Character shortFlag) {
		super(name, tooltip, category, shortFlag, "", true);
	}
	
	/**
	 * This method is called with a boolean value of true if this flag has been set by the user.
	 * It should edit the parameter object appropriately.
	 * 
	 * @param params - parameter object to be edited
	 * @param flagSet - true if this flag has been set
	 */
	protected abstract void process(P params, boolean flagSet);
	
	@Override
	public boolean isValid(NullComponent c) {
		return true;
	}

	@Override
	public NullComponent createComponent(Workspace w) {
		return new NullComponent();
	}

	@Override
	public void process(P params, NullComponent c) {
		this.process(params, true);
	}

	@Override
	public void parse(ProgressListener l, P params, String[] args) {
		if(args.length > 0){
			String warning = "Argument \"" + this.getName() + "\" takes no arguments, but received arguments:";
			for(String arg : args){
				warning += " " + arg;
			}
			ErrorHandler.getInstance().handleWarning(warning, JProbeActivator.getBundle());
		}
		this.process(params, true);
	}

}
