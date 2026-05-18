package com.tcs.ilp.servease.dto;

public class ServiceSummaryDTO {

    private long newTickets;
    private long pendingAssignment;
    private long inProgress;

    public long getNewTickets() { return newTickets; }
    public void setNewTickets(long newTickets) {
        this.newTickets = newTickets;
    }

    public long getPendingAssignment() { return pendingAssignment; }
    public void setPendingAssignment(long pendingAssignment) {
        this.pendingAssignment = pendingAssignment;
    }

    public long getInProgress() { return inProgress; }
    public void setInProgress(long inProgress) {
        this.inProgress = inProgress;
    }
}
