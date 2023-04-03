package com.Me.ShiftBoard.Services.Schedule;


import com.Me.ShiftBoard.Models.Employee;
import com.Me.ShiftBoard.Models.Schedule;
import com.Me.ShiftBoard.Repositorys.ScheduleRepository;
import com.Me.ShiftBoard.Services.Employee.EmployeeService;
import com.Me.ShiftBoard.Services.SequenceGeneratorService;
import com.Me.ShiftBoard.UtilityClasses.Response;
import com.Me.ShiftBoard.UtilityClasses.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private  final EmployeeService employeeService;
    private final ScheduleRepository scheduleRepository;
    private final SequenceGeneratorService sequenceGeneratorService;



    public Response setDaySchedule(long employeeId, Schedule daySchedule, LocalDate date) {

        Response response = new Response();


        if(date.isBefore(LocalDate.now()))
        {
            response.setOperationStatus(Status.Failure,"No longer avialble to change");
            return response;
        }

        if(employeeService.existsEmployee(employeeId))
        {
            if(!scheduleRepository.existsScheduleByScheduleId(daySchedule.getScheduleId()))
            {
                daySchedule.setId(sequenceGeneratorService.generateSequence(Schedule.SEQUENCE_NAME));

                scheduleRepository.save(daySchedule);

            }
            Employee e = employeeService.getEmployeeByExternalId(employeeId);
            HashMap<LocalDate,Long> sch = e.getSchedule();
            if(sch.containsKey(date))
            {
                sch.replace(date, daySchedule.getScheduleId());
            }
            else {

                sch.put(date,daySchedule.getScheduleId());
            }
            e.setSchedule(sch);
            employeeService.saveEmployee(e);

            response.setOperationStatus(Status.Success,e);
        }
        response.setOperationStatus(Status.Failure,"No such Employee found!!");

        return response;
    }


    public Response getSchedule(long employeeId) {

        Response response = new Response();

        if(employeeService.existsEmployee(employeeId)) {

            Employee e = employeeService.getEmployeeByExternalId(employeeId);

            HashMap<LocalDate,Schedule> schedule = new HashMap<>();

            e.getSchedule().forEach((day,scheduleId) -> schedule.put(day,scheduleRepository.findScheduleByScheduleId(scheduleId)));

            response.setOperationStatus(Status.Success,schedule);
        }
        else {
            response.setOperationStatus(Status.Failure,"No such Employee found!!");
        }

        return response;
    }

    public Response getALlSchedules() {

        Response response = new Response();

        response.setOperationStatus(Status.Success,scheduleRepository.findAll());

        return response;
    }

    public Response deleteDaySchedule(long employeeId, LocalDate day) {

        Response response = new Response();

        if(day.isBefore(LocalDate.now()))
        {
            response.setOperationStatus(Status.Failure,"No longer avialble to change");
            return response;
        }

        if(employeeService.existsEmployee(employeeId))
        {
            Employee e = employeeService.getEmployeeByExternalId(employeeId);
            HashMap<LocalDate,Long> schedule = e.getSchedule();
            schedule.replace(day, (long) -1);
            e.setSchedule(schedule);
            employeeService.saveEmployee(e);

            response.setOperationStatus(Status.Success,e);

        }
        else{
            response.setOperationStatus(Status.Failure,"No such Employee found!!");
        }

        return response;
    }


    public Response getScheduelForDay(LocalDate date) {

        Response response = new Response();
        List<Employee> employees = employeeService.getEmployeesForOtherService();
        List<Employee> finalEmployees ;
        finalEmployees = employees.stream().filter(employee -> employee.getSchedule().get(date) != -1).collect(Collectors.toList());
        response.setOperationStatus(Status.Success,finalEmployees);
        return  response;
    }

    public Response getScheduleFromId(long scheduleId) {

        Response response = new Response();
        if(scheduleRepository.existsScheduleByScheduleId(scheduleId))
        {
            response.setOperationStatus(Status.Success,scheduleRepository.findScheduleByScheduleId(scheduleId));
        }else {
            response.setOperationStatus(Status.Failure,"No such schedule present!");
        }
        return response;
    }
}
