package com.tcs.ilp.servease.exception;

/**
 * Base exception for ServiceCompletion module
 */
public class ServiceCompletionExceptionMain extends Exception {

    public ServiceCompletionExceptionMain() {
        super();
    }

    public ServiceCompletionExceptionMain(String message) {
        super(message);
    }

    /**
     * Thrown when a ServiceCompletion record is not found
     */
    public static class ServiceCompletionNotFoundException
            extends ServiceCompletionExceptionMain {

        public ServiceCompletionNotFoundException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when duplicate completion_id is inserted
     */
    public static class DuplicateServiceCompletionException
            extends ServiceCompletionExceptionMain {

        public DuplicateServiceCompletionException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when ServiceCompletion data is invalid
     */
    public static class InvalidServiceCompletionException
            extends ServiceCompletionExceptionMain {

        public InvalidServiceCompletionException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when DAO / database access fails
     */
    public static class ServiceCompletionDataAccessException
            extends ServiceCompletionExceptionMain {

        public ServiceCompletionDataAccessException(String message) {
            super(message);
        }
    }
}