package com.Me.ShiftBoard.Models;


import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@Document(collection = "DaySchedule")
public class Schedule {


    @Transient
    public static final String SEQUENCE_NAME = "schedule_sequence";

    @Id
    private long id;
    private long scheduleId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime lunchStartTime;
    private LocalTime lunchEndTime;

    private long totalHour;


    public void setTotalHour()
    {
        Duration d = Duration.between(startTime,endTime);
        Duration l = Duration.between(lunchStartTime,lunchEndTime);
        long seconds = d.getSeconds() - l.getSeconds();
        this.totalHour = (seconds/60) / 60 ;


    }

}
