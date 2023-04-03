package com.Me.ShiftBoard.Repositorys;


import com.Me.ShiftBoard.Models.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ScheduleRepository extends MongoRepository<Schedule,Integer>, QuerydslPredicateExecutor<Schedule> {


    boolean existsScheduleByScheduleId(long scheduleID);

    Schedule findScheduleByScheduleId(long scheduleIO);

}
