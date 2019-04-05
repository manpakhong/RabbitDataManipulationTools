package hksarg.swd.csss.csa.flowtest.helpers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.models.vos.CaptureVo;

public class CaptureVoHelper {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	public CaptureVoHelper() {

	}

	public boolean areCaptureVoValueEquivalent(CaptureVo captureVo1, CaptureVo captureVo2) {
		boolean areEquivalent = false;

		return areEquivalent;
	}

	public List<CaptureVo> cloneList(List<CaptureVo> list) throws Exception {
		List<CaptureVo> clone = null;
		try {
			clone = new ArrayList<CaptureVo>(list.size());
			for (CaptureVo item : list) {
				clone.add((CaptureVo) item.clone());
			}
		} catch (Exception e) {
        	logger.error(className + ".cloneList() - ",e);
        	throw e;
		}
		return clone;
	}

}
