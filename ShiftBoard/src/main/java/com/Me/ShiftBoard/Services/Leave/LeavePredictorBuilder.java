package com.Me.ShiftBoard.Services.Leave;


import com.Me.ShiftBoard.Models.Leave;
import com.Me.ShiftBoard.Querydsl.QLeave;
import com.Me.ShiftBoard.UtilityClasses.Criteria;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class LeavePredictorBuilder {

    public static BooleanBuilder getSearchPredicates(Map<String,String> params)
    {
        BooleanBuilder builder = new BooleanBuilder();

        QLeave qLeave = new QLeave("leave");

        List<Criteria> criterias = Criteria.getEqualCriteria(params);

        criterias.forEach( criteria -> {

            if(criteria.getKey().equals("employeeId"))
            {
                builder.and(qLeave.employeeId.eq(Long.valueOf(criteria.getValue())));
            }
            if(criteria.getKey().equals("date"))
            {
                builder.and(qLeave.date.eq(LocalDate.parse(criteria.getValue())));
            }
            if(criteria.getKey().equals("category"))
            {
                String value = criteria.getValue();
                if(value.equals("RELIGION_LEAVE"))
                {
                    builder.and(qLeave.category.eq(Leave.LeaveCategory.RELIGION_LEAVE));
                }
                if(value.equals("SICK_LEAVE"))
                {
                    builder.and(qLeave.category.eq(Leave.LeaveCategory.SICK_LEAVE));
                }
            }
            if(criteria.getKey().equals("state"))
            {
                String value = criteria.getValue();
                if(value.equals("PENDING"))
                {
                    builder.and(qLeave.state.eq(Leave.State.PENDING));

                }
                if(value.equals("APPROVED"))
                {
                    builder.and(qLeave.state.eq(Leave.State.APPROVED));
                }
                if(value.equals("DECLINED"))
                {
                    builder.and(qLeave.state.eq(Leave.State.DECLINED));
                }

            }

        });
        return builder;
    }
}
