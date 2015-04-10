package com.timer;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component("checkOpenTimeJob")
public class CheckOpenTime extends QuartzJobBean {

	public CheckOpenTime() {
		// System.out.println("-------------------------------初始化------------------------");
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
	}

	public void updateSignUp() {

	}
}
