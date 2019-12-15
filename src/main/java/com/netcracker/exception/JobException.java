package com.netcracker.exception;

import com.netcracker.services.JobService;

public class JobException extends RuntimeException {
    private static final String JOB_DEFAULT_MESSAGE = "Operation is not valid: ";
    private JobService jobService;

    public JobException() {
    }

    public JobService getJobService() {
        return jobService;
    }

    public JobException(String message, JobService jobService) {
        super(message);
        this.jobService = jobService;
    }

    public JobException(String message) {
        super(message);
    }
}
