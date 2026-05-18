package com.tcs.ilp.servease.bo;

import java.util.List;
import com.tcs.ilp.servease.entity.BillUser;
import com.tcs.ilp.servease.exception.BillUserException;

public interface BillUserBO {

    void addBillUser(BillUser billUser) throws BillUserException;

    BillUser getBillUserByAssignmentId(String assignmentId)
            throws BillUserException;

    List<BillUser> getAllBillUsers() throws BillUserException;
}