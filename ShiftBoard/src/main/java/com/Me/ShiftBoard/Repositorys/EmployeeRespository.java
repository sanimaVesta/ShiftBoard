package com.Me.ShiftBoard.Repositorys;

import com.Me.ShiftBoard.Models.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface EmployeeRespository  extends MongoRepository<Employee,Integer> , QuerydslPredicateExecutor<Employee>{
    boolean existsByExternalId(long externalId);

    Employee deleteEmployeeByExternalId(long externalId);

    Employee findEmployeeByExternalId(long eId);


}
