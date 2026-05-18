package com.tcs.ilp.servease.exception;

public class ApplianceExceptions {

    // ✅ 1. Appliance Not Found
    public static class CustomerNotFoundException extends RuntimeException {
        public CustomerNotFoundException(String message) {
            super(message);
        }
    }

    // ✅ 2. Invalid Warranty Status
    public static class InvalidWarrantyStatusException extends RuntimeException {
        public InvalidWarrantyStatusException(String message) {
            super(message);
        }
    }

    // ✅ 3. Invalid Date Exception
    public static class InvalidDateException extends RuntimeException {
        public InvalidDateException(String message) {
            super(message);
        }
    }

    // ✅ 4. Duplicate Appliance Exception
    public static class DuplicateApplianceException extends RuntimeException {
        public DuplicateApplianceException(String message) {
            super(message);
        }
    }
}
