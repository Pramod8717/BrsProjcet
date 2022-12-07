package com.brs.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class StepExecListner implements StepExecutionListener {
	Logger log = LoggerFactory.getLogger(StepExecListner.class);
	@Override
	public void beforeStep(StepExecution stepExecution) {
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("     ");
		log.info(":::::::::::::::::After step execution:::::::::::::::::::");
		log.info("     ");
		
		log.info("------------------------------------------------------------------------------------");
//        log.info("StepExecutionListener - afterStep:getCommitCount=" +  stepExecution.getCommitCount());
//        log.info("StepExecutionListener - afterStep:getFilterCount=" +  stepExecution.getFilterCount());
//        log.info("StepExecutionListener - afterStep:getProcessSkipCount=" +  stepExecution.getProcessSkipCount());
//        log.info("StepExecutionListener - afterStep:getReadCount=" +  stepExecution.getReadCount());
//        log.info("StepExecutionListener - afterStep:getReadSkipCount=" +  stepExecution.getReadSkipCount());
//        log.info("StepExecutionListener - afterStep:getRollbackCount=" +  stepExecution.getRollbackCount());
//        log.info("StepExecutionListener - afterStep:getWriteCount=" +  stepExecution.getWriteCount());
//        log.info("StepExecutionListener - afterStep:getWriteSkipCount=" +  stepExecution.getWriteSkipCount());
//        log.info("StepExecutionListener - afterStep:getStepName=" +  stepExecution.getStepName());
        log.info("StepExecutionListener - afterStep:getSummary=" +  stepExecution.getSummary());
        log.info("StepExecutionListener - afterStep:getStartTime=" +  stepExecution.getStartTime());
        log.info("StepExecutionListener - afterStep:getEndTime=" +  stepExecution.getEndTime());
        log.info("StepExecutionListener - afterStep:getLastUpdated=" +  stepExecution.getLastUpdated());
        log.info("StepExecutionListener - afterStep:getExitStatus=" +  stepExecution.getExitStatus());
        log.info("StepExecutionListener - afterStep:getFailureExceptions=" +  stepExecution.getFailureExceptions());
        log.info("------------------------------------------------------------------------------------");

		return null;
	}

}
