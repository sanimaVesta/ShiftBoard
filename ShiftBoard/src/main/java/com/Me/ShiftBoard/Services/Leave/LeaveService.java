package com.Me.ShiftBoard.Services.Leave;


import com.Me.ShiftBoard.Models.Employee;
import com.Me.ShiftBoard.Models.Leave;
import com.Me.ShiftBoard.Repositorys.LeaveRepository;
import com.Me.ShiftBoard.Services.Employee.EmployeeService;
import com.Me.ShiftBoard.Services.SequenceGeneratorService;
import com.Me.ShiftBoard.UtilityClasses.Response;
import com.Me.ShiftBoard.UtilityClasses.Status;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRepository leaveRepository;

    private  final EmployeeService employeeService;
    private final SequenceGeneratorService sequenceGeneratorService;
    public Response getAllLeaves()
    {
        Response response = new Response();
        response.setOperationStatus(Status.Success,leaveRepository.findAll());
        return response;
    }

    public Response addLeave(Leave leave) {

        Response response = new Response();

        if(leave.getDate().isBefore(LocalDate.now()))
        {
            response.setOperationStatus(Status.Failure,"The date is no longer availbale");
            return response;
        }

        if(leaveRepository.existsLeaveByDateAndEmployeeId(leave.getDate(), leave.getEmployeeId())){
            response.setOperationStatus(Status.Failure,"Such leave already present!1");
            return response;
        }

        if(employeeService.existsEmployee(leave.getEmployeeId()))
        {
            Employee e = employeeService.getEmployeeByExternalId(leave.getEmployeeId());
           leave.setLeaveId(sequenceGeneratorService.generateSequence(Leave.SEQUENCE_NAME));
           leave.setState(Leave.State.PENDING);
           leaveRepository.save(leave);
           List<Long> ls= e.getLeaves();
           ls.add(leave.getLeaveId());
           e.setLeaves(ls);
           employeeService.saveEmployee(e);
           response.setOperationStatus(Status.Success,leave);
        }
        else{
            response.setOperationStatus(Status.Failure,"No such employee found!!");
        }

        return response;
    }

    public Response getLeaveByEmployee(long employeeId) {

        Response response = new Response();
        if(employeeService.existsEmployee(employeeId))
        {
            Employee e = employeeService.getEmployeeByExternalId(employeeId);

            response.setOperationStatus(Status.Success,e.getLeaves().stream().map(leaveRepository::findLeaveByLeaveId).collect(Collectors.toList()));
        }else {
            response.setOperationStatus(Status.Failure,"No such employee found!1");
        }
        return response;
    }

    public Response updateLeave(Leave leave) {

        Response response = new Response();
        Response r = checkSate(leave.getLeaveId());
        if(r.getOperationStatus().equals(Status.Failure))
        {
            return r;
        }
        if(leave.getDate().isBefore(LocalDate.now()))
        {
            response.setOperationStatus(Status.Failure,"The date is no longer availbale");
            return response;
        }


        if(employeeService.existsEmployee(leave.getEmployeeId()))
        {
            if(leaveRepository.existsLeaveByLeaveId(leave.getLeaveId()))
            {
                Leave l = leaveRepository.findLeaveByLeaveId(leave.getLeaveId());

                if(!leave.getDate().equals(l.getDate())) {
                    if (leaveRepository.existsLeaveByDateAndEmployeeId(leave.getDate(), leave.getEmployeeId())) {
                        response.setOperationStatus(Status.Failure, "Such leave already present!1");
                        return response;
                    }
                }
                l.setCategory(leave.getCategory());
                l.setDate(leave.getDate());
                l.setReason(leave.getReason());
                l.setState(leave.getState());
                leaveRepository.save(l);
                response.setOperationStatus(Status.Success,l);
            }
            else{
                response.setOperationStatus(Status.Failure,"No such leave found!!");
            }
        }
        else{
            response.setOperationStatus(Status.Failure,"No Such employee found!!");
        }

        return response;
    }

    public Response deleteLeave(long leaveId) {
        Response response = new Response();
        Response r = checkSate(leaveId);
        if(r.getOperationStatus().equals(Status.Failure))
        {
            return r;
        }
        if(leaveRepository.existsLeaveByLeaveId(leaveId))
        {
            Leave l = leaveRepository.deleteLeaveByLeaveId(leaveId);
            Employee e = employeeService.getEmployeeByExternalId(l.getEmployeeId());
            List<Long> ls= e.getLeaves();
            ls.remove(l.getLeaveId());
            employeeService.saveEmployee(e);
            response.setOperationStatus(Status.Success,l);
        }else{
            response.setOperationStatus(Status.Failure,"No such leave exists!1");
        }

        return response;
    }


    public Response checkSate(long leaveId)
    {
        Response response = new Response();
        Leave l = leaveRepository.findLeaveByLeaveId(leaveId);
        if(!l.getState().equals(Leave.State.PENDING))
        {
            response.setOperationStatus(Status.Failure,"This leave cannot be modified as desicion already has been made!");
            return  response;
        }
        response.setOperationStatus(Status.Success,"");
        return response;
    }

    public Response getLeavesBySearch(Map<String,String> params)
    {
        Response response = new Response();

        BooleanBuilder builder = LeavePredictorBuilder.getSearchPredicates(params);

        List<Leave> leaves = new ArrayList<>();


        leaveRepository.findAll(builder).iterator().forEachRemaining(leaves::add);

        if(leaves.size() == 0)
        {
            response.setOperationStatus(Status.Failure,"No such Leaves found!!");

        }else {
            response.setOperationStatus(Status.Success,leaves);
        }

        return response;
    }
}
