package com.rabbitforever.datamanipulation.commands;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.DbProperties;
import com.rabbitforever.datamanipulation.models.dtos.CaptureDto;
import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;
import com.rabbitforever.datamanipulation.services.SnapshotMgr;

public class CaptureXmlFile {
	private final static Logger logger = LoggerFactory.getLogger(CaptureXmlFile.class);
	private final static String className = CaptureXmlFile.class.getName();
	public static void main(String[] args) throws Exception {
		DbProperties db2DbProperties = PropertiesFactory.getInstanceOfDb2DbProperties();
		String schema = db2DbProperties.getSchema();
		SnapshotMgr snapshotMgr = new SnapshotMgr();
		
		// check all dependencies tables
		List<String> allDependentTableNameList = snapshotMgr.getAllDependentTableNameList("actor");
		for (String dependentTableName: allDependentTableNameList){
			if (logger.isInfoEnabled()){
				logger.info(className + ".main() - dependentTableName=" + dependentTableName);
			}
		}
		
		
		
		// please beware of mysql needs lower case table name
		
		// --- mandatory inputs - 1
		String scopeFileName = "filmScope";
		String scopeFolderName = "filmScopeFolder";

		CaptureScopeDto captureScopeDto = new CaptureScopeDto();
		captureScopeDto.setScopeFileName(scopeFileName);
		captureScopeDto.setScopeFolderName(scopeFolderName);
		
		// --- mandatory inputs - 2
		CaptureDto captureDto1 = new CaptureDto();
		captureDto1.setTableName("actor");
		captureDto1.setCaptureSql("select * from actor where actor_id = 8");
		captureDto1.setOutputXmlFileName("actor_snapshot.xml");
		captureScopeDto.addCaptureDto(captureDto1);		
		
		CaptureDto captureDto2 = new CaptureDto();
		captureDto2.setTableName("film");
		captureDto2.setCaptureSql("SELECT f.film_id, f.title, f.description, f.language_id, f.original_language_id, f.rental_duration, f.rental_rate, f.LENGTH, f.replacement_cost, f.rating, f.special_features, f.last_update FROM film f inner join film_actor fa on fa.film_id = f.film_id where fa.actor_id = 8 ");
		captureDto2.setOutputXmlFileName("film_snapshot.xml");
		captureScopeDto.addCaptureDto(captureDto2);		
		
		CaptureDto captureDto3 = new CaptureDto();
		captureDto3.setTableName("film_actor");
		captureDto3.setCaptureSql("select * from film_actor where actor_id = 8");
		captureDto3.setOutputXmlFileName("film_actor_snapshot.xml");
		captureScopeDto.addCaptureDto(captureDto3);		
		
		CaptureDto captureDto4 = new CaptureDto();
		captureDto4.setTableName("film_category");
		captureDto4.setCaptureSql("select * from film_category where film_id in (select film_id from film_actor where actor_id = 8)");
		captureDto4.setOutputXmlFileName("film_category_snapshot.xml");
		captureScopeDto.addCaptureDto(captureDto4);		
		
		CaptureDto captureDto5 = new CaptureDto();
		captureDto5.setTableName("inventory");
		captureDto5.setCaptureSql("select * from inventory where film_id in (select film_id from film_actor where actor_id = 8)");
		captureDto5.setOutputXmlFileName("inventory_snapshot.xml");
		captureScopeDto.addCaptureDto(captureDto5);	
		
		CaptureDto captureDto6 = new CaptureDto();
		captureDto6.setTableName("rental");
		captureDto6.setCaptureSql("select * from rental where inventory_id in (select distinct i.inventory_id from inventory i inner join film_actor fa on i.film_id = fa.film_id)");
		captureDto6.setOutputXmlFileName("rental_snapshot.xml");
		captureScopeDto.addCaptureDto(captureDto6);	
		
		CaptureDto captureDto7 = new CaptureDto();
		captureDto7.setTableName("language");
		captureDto7.setCaptureSql("select * from language");
		captureDto7.setOutputXmlFileName("language_snapshot.xml");
		captureScopeDto.addCaptureDto(captureDto7);	
		
		
		/*
		// --- mandatory inputs - 1
		String scopeFileName = "tcsOffrRpt";
		String scopeFolderName = "tcsOffrRptScope";

		CaptureScopeDto captureScopeDto = new CaptureScopeDto();
		captureScopeDto.setScopeFileName(scopeFileName);
		captureScopeDto.setScopeFolderName(scopeFolderName);
		
		// --- mandatory inputs - 2
		CaptureDto captureDto1 = new CaptureDto();
		captureDto1.setTableName("TCS_OFFR_RPT");
		captureDto1.setSql("SELECT * FROM " + schema + ".TCS_OFFR_RPT WHERE CASE_KEY = 3748");
		captureDto1.setOutputXmlFileName("tcsoffrrpt-snapshot.xml");
		captureScopeDto.addCaptureDto(captureDto1);		
		*/
//		
//		CaptureDto captureDto2 = new CaptureDto();
//		captureDto2.setTableName("TMS_MMO_LTR_CTT");
//		captureDto2.setSql("select * from " + schema + ".TMS_MMO_LTR_CTT");
//		captureDto2.setOutputXmlFileName("tms_mmo_ltr_ctt-snapshot.xml");
//		
//		captureScopeDto.addCaptureDto(captureDto2);
		
//		CaptureDto captureDto3 = new CaptureDto();
//		captureDto3.setTableName("film");
//		captureDto3.setSql("SELECT f.film_id, f.title, f.description, f.language_id, f.original_language_id, f.rental_duration, f.rental_rate, f.LENGTH, f.replacement_cost, f.rating, f.special_features, f.last_update FROM film f inner join film_actor fa on fa.film_id = f.film_id where fa.actor_id = 8");
//		captureDto3.setOutputXmlFileName("film-snapshot-1.xml");
//		
//		captureScopeDto.addCaptureDto(captureDto3);
		

		snapshotMgr.captureXmlFiles(captureScopeDto);
		
		StringBuilder sbResult = new StringBuilder();
		sbResult.append("Scope file: " + scopeFileName + "\n");
		sbResult.append("---------------------------------------------------\n");
		sbResult.append("xml file list: " + captureScopeDto.getCaptureDtoList() + "\n");
		
		if (logger.isInfoEnabled()){
			logger.info(className + ".main() - sbResult=" + sbResult);
		}

	}

}
