package com.Me.ShiftBoard.Controllers;


import com.Me.ShiftBoard.Models.Schedule;
import com.Me.ShiftBoard.Services.Schedule.ScheduleService;
import com.Me.ShiftBoard.UtilityClasses.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {


    private final ScheduleService scheduleService;

    @PostMapping("/employee/{employeeId}")
    public Response setDaySchedule(@PathVariable("employeeId") long employeeId, @RequestBody Schedule daySchedule, @RequestParam(value = "dt", required = true) String dt){
        LocalDate date1 = LocalDate.parse(dt);
        return scheduleService.setDaySchedule(employeeId,daySchedule, date1);
    }

    @GetMapping("/employee/{employeeId}")
    public Response getScheduleForEmployee(@PathVariable long employeeId)
    {
        return  scheduleService.getSchedule(employeeId);
    }


    @DeleteMapping("/employee/{employeeId}")
    public Response deleteScheduleForEmployee(@PathVariable long employeeId,@RequestParam(value = "dt", required = true) String dt)
    {
        return scheduleService.deleteDaySchedule(employeeId,LocalDate.parse(dt));
    }

    @GetMapping("")
    public Response getAllSchedule()
    {
        return scheduleService.getALlSchedules();
    }

    @GetMapping("/day")
    public Response getScheduleForDay(@RequestParam(value = "dt") String dt)
    {
        return scheduleService.getScheduelForDay(LocalDate.parse(dt));
    }

    @GetMapping("/{scheduleId}")
    public Response getScheduleFromId(@PathVariable long scheduleId)
    {
        return scheduleService.getScheduleFromId(scheduleId);
    }

}
