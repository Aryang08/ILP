package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ilp.servease.entity.BillUser;
import com.tcs.ilp.servease.exception.BillUserException;
import com.tcs.ilp.servease.repository.BillUserRepository;

@Service
public class BillUserBOImpl implements BillUserBO {

    @Autowired
    private BillUserRepository billUserRepository;

    // ✅ ADD (WRITE → needs transaction)
    @Override
    @Transactional
    public void addBillUser(BillUser billUser) {

        if (billUser == null) {
            throw new BillUserException("BillUser object cannot be null");
        }

        if (billUser.getAmount() == null || billUser.getAmount().doubleValue() <= 0) {
            throw new BillUserException("Bill amount must be greater than zero");
        }

        billUserRepository.save(billUser);
    }

    // ✅ GET BY ASSIGNMENT ID (READ → use readonly)
    @Override
    @Transactional(readOnly = true)
    public BillUser getBillUserByAssignmentId(String assignmentId) {

        BillUser billUser = billUserRepository.findByAssignmentId(assignmentId);

        if (billUser == null) {
            throw new BillUserException(
                    "No BillUser found for Assignment ID: " + assignmentId
            );
        }

        return billUser;
    }

    // ✅ GET ALL (READ → use readonly)
    @Override
    @Transactional(readOnly = true)
    public List<BillUser> getAllBillUsers() {

        List<BillUser> billUsers = billUserRepository.findAll();

        if (billUsers.isEmpty()) {
            throw new BillUserException("No BillUser records found");
        }

        return billUsers;
    }
}
