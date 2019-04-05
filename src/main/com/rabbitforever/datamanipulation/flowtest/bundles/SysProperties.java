package com.rabbitforever.datamanipulation.flowtest.bundles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysProperties extends PropertiesBase {
	public static final String DATABASE_TYPE_DB2 = "db2";
	public static final String DATABASE_TYPE_MYSQL = "mysql";
	public static final String DATABASE_TYPE_MSSQL = "mssql";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private final static String FILE_NAME = "sys.properties";
	private String testFolderRoot;
	private String testObjectRoot;
	private String testObjectExt;
	private String scopeFileNameExt;
	private String databaseType;
	private Boolean flatXmlStyle;
	private String notePadProgram;
	private Integer consumerThreadPool;
	private Integer consumerSleepTime;
	private Integer producerThreadPool;
	private Integer producerSleepTime;
	private Integer threadQueueSize;
	private Boolean resultLogEnabled;
	private String resultLogFolderRoot;
	private String resultLogPrefix;
	private Boolean resultLogNetworkDriveEnabled;
	private String resultLogNetworkDriveFolderRootPath;
	private String resultLogFileNameExt;
	private Boolean deleteStatementGenerateButtonEnabled;
	private Integer deletePercentageOfKeyColumnsHit;
	private Boolean deleteConsequentKeyColumnsCheckEnabled;
	private Boolean deleteCheckFirstKeySeqKeyColumnsHitEnabled;
	private String deleteStatementResultFilesName;
	private String deleteStatementResultFilesNameExt;
	private Boolean deleteThenRestoreComboActionEnabled;
	private String deleteThenRestoreBackupFolderName;
	private String buttonsIconsRoot;
	private Boolean deleteStatementGenerateAutoOpenFolder;
	private Boolean deleteStatementGenerateAutoOpenFile;
	private Boolean restoreInAscendingOrder;
	
	public SysProperties() throws Exception {
		super(FILE_NAME);
	}



	public String getTestFolderRoot() {
		testFolderRoot = this.getPropValues("test_folder_root");
		return testFolderRoot;
	}

	public String getTestObjectRoot() {
		testObjectRoot = this.getPropValues("test_object_root");
		return testObjectRoot;
	}

	public String getTestObjectExt() {
		testObjectExt = this.getPropValues("test_object_ext");
		return testObjectExt;
	}

	public String getScopeFileNameExt() {
		scopeFileNameExt = this.getPropValues("scope_file_name_ext");
		return scopeFileNameExt;
	}

	public String getDatabaseType() {
		databaseType = this.getPropValues("database_type");
		return databaseType;
	}

	public Boolean getFlatXmlStyle() {
		try {
			String flatXmlStyleString = this.getPropValues("flat_xml_style");
			flatXmlStyle = Boolean.parseBoolean(flatXmlStyleString);
		} catch (Exception e) {
			logger.error(className + ".getFlatXmlStyle()", e);
			flatXmlStyle = true;
		}
		return flatXmlStyle;
	}

	public Integer getConsumerThreadPool() {
		try {
			String consumerThreadPoolString = this.getPropValues("consumer_thread_pool");
			consumerThreadPool = Integer.parseInt(consumerThreadPoolString);
		} catch (Exception e) {
			logger.error(className + ".getConsumerThreadPool()", e);
		}
		return consumerThreadPool;
	}

	public Integer getConsumerSleepTime() {
		try {
			String consumerSleepTimeString = this.getPropValues("consumer_sleep_time");
			consumerSleepTime = Integer.parseInt(consumerSleepTimeString);
		} catch (Exception e) {
			logger.error(className + ".getConsumerSleepTime()", e);
		}
		return consumerSleepTime;
	}

	public Integer getProducerThreadPool() {
		String producerThreadPoolString = this.getPropValues("producer_thread_pool");
		producerThreadPool = Integer.parseInt(producerThreadPoolString);
		return producerThreadPool;
	}

	public Integer getProducerSleepTime() {
		try {
			String producerSleepTimeString = this.getPropValues("producer_sleep_time");
			producerSleepTime = Integer.parseInt(producerSleepTimeString);
		} catch (Exception e) {
			logger.error(className + ".getProducerSleepTime()", e);
		}
		return producerSleepTime;
	}

	public Integer getThreadQueueSize() {
		try {
			String threadQueueSizeString = this.getPropValues("thread_queue_size");
			threadQueueSize = Integer.parseInt(threadQueueSizeString);
		} catch (Exception e) {
			logger.error(className + ".getThreadQueueSize()", e);
		}
		return threadQueueSize;
	}

	public Boolean getResultLogEnabled() {
		try {
			String resultLogEnabledString = this.getPropValues("result_log_enabled");
			resultLogEnabled = Boolean.parseBoolean(resultLogEnabledString);
		} catch (Exception e) {
			logger.error(className + ".getResultLogEnabled()", e);
			resultLogEnabled = false;
		}
		return resultLogEnabled;
	}

	public String getResultLogFolderRoot() {
		resultLogFolderRoot = this.getPropValues("result_log_folder_root");
		return resultLogFolderRoot;
	}

	public String getResultLogPrefix() {
		resultLogPrefix = this.getPropValues("result_log_prefix");
		return resultLogPrefix;
	}

	public Boolean getResultLogNetworkDriveEnabled() {
		try {
			String resultLogNetworkDriveEnabledString = this.getPropValues("result_log_network_drive_enabled");
			resultLogNetworkDriveEnabled = Boolean.parseBoolean(resultLogNetworkDriveEnabledString);
		} catch (Exception e) {
			logger.error(className + ".getResultLogNetworkDriveEnabled()", e);
			resultLogNetworkDriveEnabled = false;
		}
		return resultLogNetworkDriveEnabled;
	}

	public String getResultLogNetworkDriveFolderRootPath() {
		resultLogNetworkDriveFolderRootPath = this.getPropValues("result_log_network_drive_folder_root_path");
		return resultLogNetworkDriveFolderRootPath;
	}

	public String getResultLogFileNameExt() {
		resultLogFileNameExt = this.getPropValues("result_log_file_name_ext");

		return resultLogFileNameExt;
	}

	public Boolean getDeleteStatementGenerateButtonEnabled() {
		try {
			String deleteStatementGenerateButtonEnabledString = this
					.getPropValues("delete_statement_generate_button_enabled");
			deleteStatementGenerateButtonEnabled = Boolean.parseBoolean(deleteStatementGenerateButtonEnabledString);
		} catch (Exception e) {
			logger.error(className + ".getDeleteStatementGenerateButtonEnabled()", e);
			deleteStatementGenerateButtonEnabled = false;
		}
		return deleteStatementGenerateButtonEnabled;
	}

	public Integer getDeletePercentageOfKeyColumnsHit() {
		try {
			String deletePercentageOfKeyColumnsHitString = this.getPropValues("delete_percentage_of_key_columns_hit");
			deletePercentageOfKeyColumnsHit = Integer.parseInt(deletePercentageOfKeyColumnsHitString);
		} catch (Exception e) {
			logger.error(className + ".getDeletePercentageOfKeyColumnsHit()", e);
			deletePercentageOfKeyColumnsHit = 100;
		}
		return deletePercentageOfKeyColumnsHit;
	}

	public Boolean getDeleteConsequentKeyColumnsCheckEnabled() {
		try {
			String deleteConsequentKeyColumnsCheckEnabledString = this
					.getPropValues("delete_consequent_key_columns_check_enabled");
			deleteConsequentKeyColumnsCheckEnabled = Boolean.parseBoolean(deleteConsequentKeyColumnsCheckEnabledString);
		} catch (Exception e) {
			logger.error(className + ".getDeleteConsequentKeyColumnsCheckEnabled()", e);
			deleteConsequentKeyColumnsCheckEnabled = false;
		}
		return deleteConsequentKeyColumnsCheckEnabled;
	}

	public Boolean getDeleteCheckFirstKeySeqKeyColumnsHitEnabled() {
		try {
			String deleteCheckFirstKeySeqKeyColumnsHitEnabledString = this
					.getPropValues("delete_check_first_key_seq_key_columns_hit_enabled");
			deleteCheckFirstKeySeqKeyColumnsHitEnabled = Boolean
					.parseBoolean(deleteCheckFirstKeySeqKeyColumnsHitEnabledString);

		} catch (Exception e) {
			logger.error(className + ".getDeleteCheckFirstKeySeqKeyColumnsHitEnabled()", e);
			deleteCheckFirstKeySeqKeyColumnsHitEnabled = true;
		}
		return deleteCheckFirstKeySeqKeyColumnsHitEnabled;
	}

	public String getDeleteStatementResultFilesName() {
		deleteStatementResultFilesName = this.getPropValues("delete_statement_result_files_name");

		return deleteStatementResultFilesName;
	}

	public String getDeleteStatementResultFilesNameExt() {
		deleteStatementResultFilesNameExt = this.getPropValues("delete_statement_result_files_name_ext");

		return deleteStatementResultFilesNameExt;
	}

	public Boolean getDeleteThenRestoreComboActionEnabled() {
		try {
			String deleteThenRestoreComboActionEnabledString = this
					.getPropValues("delete_then_restore_combo_action_enabled");
			deleteThenRestoreComboActionEnabled = Boolean.parseBoolean(deleteThenRestoreComboActionEnabledString);
		} catch (Exception e) {
			logger.error(className + ".getDeleteConsequentKeyColumnsCheckEnabled()", e);
			deleteThenRestoreComboActionEnabled = false;
		}
		return deleteThenRestoreComboActionEnabled;
	}

	public String getDeleteThenRestoreBackupFolderName() {
		deleteThenRestoreBackupFolderName = this.getPropValues("delete_then_restore_backup_folder_name");
		return deleteThenRestoreBackupFolderName;
	}
	
	public Boolean getDeleteStatementGenerateAutoOpenFolder() {
		try {
			String deleteStatementGenerateAutoOpenFolderString = this
					.getPropValues("delete_statement_generate_auto_open_folder");
			deleteStatementGenerateAutoOpenFolder = Boolean.parseBoolean(deleteStatementGenerateAutoOpenFolderString);
		} catch (Exception e) {
			logger.error(className + ".getDeleteStatementGenerateAutoOpenFolder()", e);
			deleteStatementGenerateAutoOpenFolder = false;
		}
		return deleteStatementGenerateAutoOpenFolder;
	}

	public Boolean getDeleteStatementGenerateAutoOpenFile() {
		try {
			String deleteStatementGenerateAutoOpenFileString = this
					.getPropValues("delete_statement_generate_auto_open_file");
			deleteStatementGenerateAutoOpenFile = Boolean.parseBoolean(deleteStatementGenerateAutoOpenFileString);
		} catch (Exception e) {
			logger.error(className + ".getDeleteStatementGenerateAutoOpenFile()", e);
			deleteStatementGenerateAutoOpenFile = false;
		}
		return deleteStatementGenerateAutoOpenFile;
	}

	public Boolean getRestoreInAscendingOrder() {
		try {
			String restoreInAscendingOrderString = this
					.getPropValues("restore_in_ascending_order");
			restoreInAscendingOrder = Boolean.parseBoolean(restoreInAscendingOrderString);
		} catch (Exception e) {
			logger.error(className + ".getRestoreInAscendingOrder()", e);
			restoreInAscendingOrder = false;
		}
		return restoreInAscendingOrder;
	}
	
	public String getNotePadProgram() {
		try {
			notePadProgram = this.getPropValues("notepad_program");
		} catch (Exception e) {
			logger.error(className + ".getNotePadProgram()", e);
		}
		return notePadProgram;
	}

	public String getButtonsIconsRoot() {
		try{
			buttonsIconsRoot = this.getPropValues("buttons_icons_root");
		} catch (Exception e){
			logger.error(className + ".getButtonsIconsRoot()", e);
		}
		return buttonsIconsRoot;
	}


	
	
}
