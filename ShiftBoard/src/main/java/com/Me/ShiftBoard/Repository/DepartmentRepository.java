package com.Me.ShiftBoard.Repository;

import com.Me.ShiftBoard.Model.Department;
import com.Me.ShiftBoard.Service.DepartmentService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DepartmentRepository  extends MongoRepository<Department,Integer>, QuerydslPredicateExecutor<Department> {

    boolean existsDepartmentByDepartmentId(long departmentId);

    Department findDepartmentByDepartmentId(long departmentId);

    Department deleteDepartmentByDepartmentId(long departmentId);

}
