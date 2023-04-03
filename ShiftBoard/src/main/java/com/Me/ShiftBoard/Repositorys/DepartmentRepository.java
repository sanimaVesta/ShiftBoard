package com.Me.ShiftBoard.Repositorys;

import com.Me.ShiftBoard.Models.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DepartmentRepository  extends MongoRepository<Department,Integer>, QuerydslPredicateExecutor<Department> {

    boolean existsDepartmentByDepartmentId(long departmentId);

    Department findDepartmentByDepartmentId(long departmentId);

    Department deleteDepartmentByDepartmentId(long departmentId);



}
