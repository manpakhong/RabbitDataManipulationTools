package com.rabbitforever.datamanipulation.models.vos;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.models.dtos.CaptureDto;
import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;

public class CaptureScopeVo extends CaptureScopeDto {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	protected List<CaptureVo> captureVoList;
	public CaptureScopeVo(CaptureScopeDto captureScopeDto) {
		this.scopeFileName = captureScopeDto.getScopeFileName();
		this.scopeFolderName = captureScopeDto.getScopeFolderName();
		List<CaptureDto> captureDtoList = captureScopeDto.getCaptureDtoList();
		syncCaptureDtoList2CaptureVoList(captureDtoList);
		this.captureDtoList = captureDtoList;
	}
	public CaptureScopeVo(){
		super();
		captureVoList = new ArrayList<CaptureVo>();
	}

	public List<CaptureVo> getCaptureVoList() {
		return captureVoList;
	}
	public void setCaptureVoList(List<CaptureVo> captureVoList) {
		syncCaptureVoList2CaptureDtoList();
		this.captureVoList = captureVoList;
	}

	private void syncCaptureDtoList2CaptureVoList(List<CaptureDto> captureDtoList){
		try{
			if (captureDtoList != null){
				if (captureVoList != null){
					captureVoList.clear();
				} else {
					captureVoList = new ArrayList<CaptureVo>();
				}
				for (CaptureDto captureDto:captureDtoList){
					CaptureVo captureVo = new CaptureVo(captureDto);
					captureVoList.add(captureVo);
				}
			} else {
				this.captureVoList = null;
			}
		} catch (Exception e){
			logger.error(className + ".syncCaptureVoList2CaptureDtoList() - ",e);
		}
	}
	
	public void syncCaptureVoList2CaptureDtoList(){
		try{
			if (captureVoList != null){
				if (captureDtoList != null){
					captureDtoList.clear();
				} else {
					captureDtoList = new ArrayList<CaptureDto>();
				}
				for (CaptureVo captureVo:captureVoList){
					CaptureDto captureDto = captureVo;
					captureDtoList.add(captureDto);
				}
			} else {
				this.captureDtoList = null;
			}
		} catch (Exception e){
			logger.error(className + ".syncCaptureVoList2CaptureDtoList() - ",e);
		}
	}
}
