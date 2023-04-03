package com.Me.ShiftBoard.Models;


import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@QueryEntity
@Document(collection = "Leaves")
public class Leave {

    @Transient
    public static final String SEQUENCE_NAME = "leave_sequence";


    @Id
    private long leaveId;
    private long employeeId;
    private LocalDate date;
    private String reason;
    private LeaveCategory category;
    private State state;

    public enum State {
        APPROVED,DECLINED,PENDING
    }
    public enum LeaveCategory {
        SICK_LEAVE,RELIGION_LEAVE
    }
}
