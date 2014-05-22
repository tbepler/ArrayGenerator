package chiptools.jprobe.function.args;

import java.util.List;

import chiptools.Constants;
import chiptools.jprobe.data.Peaks;
import chiptools.jprobe.function.params.PeaksParam;
import jprobe.services.JProbeCore;
import jprobe.services.function.DataArgument;

public class PeaksArgument extends DataArgument<PeaksParam, Peaks>{

	public PeaksArgument(JProbeCore core, boolean optional) {
		super(
				core,
				Constants.getName(PeaksArgument.class),
				Constants.getDescription(PeaksArgument.class),
				Constants.getCategory(PeaksArgument.class),
				optional,
				Peaks.class,
				1,
				1,
				false
				);
	}

	@Override
	protected void process(PeaksParam params, List<Peaks> data) {
		params.setPeaks(data.get(0));
	}

}