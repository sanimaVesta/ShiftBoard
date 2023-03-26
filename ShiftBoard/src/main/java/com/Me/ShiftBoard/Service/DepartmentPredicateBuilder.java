package com.Me.ShiftBoard.Service;

import com.Me.ShiftBoard.Model.Department;
import com.Me.ShiftBoard.Model.Employee;
import com.Me.ShiftBoard.Querydsl.QDepartment;
import com.Me.ShiftBoard.Querydsl.QEmployee;
import com.Me.ShiftBoard.UtilityClasses.Criteria;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentPredicateBuilder {


    public static BooleanBuilder getSearchPredicates(Map<String,String> params)
    {
        BooleanBuilder builder = new BooleanBuilder();

        QDepartment qDepartment = new QDepartment("department");

        List<Criteria> criterias = Criteria.getEqualCriteria(params);

        criterias.forEach( criteria -> {

            if(criteria.getKey().equals("name"))
            {
                builder.and(qDepartment.name.contains(criteria.getValue()));
            }
            if(criteria.getKey().equals("email"))
            {
                builder.and(qDepartment.email.eq(criteria.getValue()));
            }
        });
        return builder;
    }
}
