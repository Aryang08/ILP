package com.tcs.ilp.servease.dto;

public class AssignmentSummaryDTO {

    private long unassigned;
    private long assigned;
    private long delayed;

    public long getUnassigned() { return unassigned; }
    public void setUnassigned(long unassigned) { this.unassigned = unassigned; }

    public long getAssigned() { return assigned; }
    public void setAssigned(long assigned) { this.assigned = assigned; }

    public long getDelayed() { return delayed; }
    public void setDelayed(long delayed) { this.delayed = delayed; }
}