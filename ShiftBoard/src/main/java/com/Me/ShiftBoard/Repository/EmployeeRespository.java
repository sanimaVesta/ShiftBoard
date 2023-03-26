package com.Me.ShiftBoard.Repository;

import com.Me.ShiftBoard.Model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface EmployeeRespository  extends MongoRepository<Employee,Integer> , QuerydslPredicateExecutor<Employee>{
    boolean existsByExternalId(long externalId);

    Employee deleteEmployeeByExternalId(long externalId);

    Employee findEmployeeByExternalId(long eId);


}
