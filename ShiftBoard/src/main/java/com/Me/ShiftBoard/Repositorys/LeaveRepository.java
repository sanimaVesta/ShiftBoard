package com.Me.ShiftBoard.Repositorys;

import com.Me.ShiftBoard.Models.Leave;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;

public interface LeaveRepository extends MongoRepository<Leave,Integer>, QuerydslPredicateExecutor<Leave> {

    boolean existsLeaveByLeaveId(long leaveId);
    Leave findLeaveByLeaveId(long leaveId);
    Leave deleteLeaveByLeaveId(long leaveId);

    boolean existsLeaveByDateAndEmployeeId(LocalDate date, long employeeId);
}
