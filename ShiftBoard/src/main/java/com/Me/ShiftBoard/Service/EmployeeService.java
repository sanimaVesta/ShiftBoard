package com.Me.ShiftBoard.Service;

import com.Me.ShiftBoard.Model.Employee;
import com.Me.ShiftBoard.Repository.DepartmentRepository;
import com.Me.ShiftBoard.Repository.EmployeeRespository;
import com.Me.ShiftBoard.UtilityClasses.Criteria;
import com.Me.ShiftBoard.UtilityClasses.Response;
import com.Me.ShiftBoard.UtilityClasses.Status;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.expression.Operation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;

@Service
public class EmployeeService {

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    EmployeeRespository employeeRespository;
    @Lazy
    @Autowired
    DepartmentService departmentService;



    public Response addEmployee(Employee employee){

        Response response = new Response();

        if(employeeRespository.existsByExternalId(employee.getExternalId()))
        {
            response.setOperationStatus(Status.Failure,"Employee with the same external Id already exists");

        }else{
            employee.setId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));
            employeeRespository.save(employee);

            if(departmentService.addEmployeeInDepartment(employee.getExternalId(),employee.getDepartmentId()))
            {
                response.setOperationStatus(Status.Success,employee);
            } else{
                employeeRespository.deleteEmployeeByExternalId(employee.getExternalId());
                response.setOperationStatus(Status.Failure,"Cannot add employee to the mentioned department!");
            }

        }
        return response;
    }

    public Response getAllEmployee()
    {
        Response response = new Response();
        response.setOperationStatus(Status.Success,employeeRespository.findAll());
        return response;
    }


    public Response getEmployee(long eId)
    {
        Response response = new Response();

        if(employeeRespository.existsByExternalId(eId))
        {
            response.setOperationStatus(Status.Success,employeeRespository.findEmployeeByExternalId(eId));
        }
        else{
            response.setOperationStatus(Status.Failure,"No such employee found.");
        }

        return response;

    }


    public Response getEmployeesBySearch(Map<String,String> params)
    {
        Response response = new Response();

        BooleanBuilder builder = EmployeePredicateBuilder.getSearchPredicates(params);

        List<Employee> employees = new ArrayList<>();


        employeeRespository.findAll(builder).iterator().forEachRemaining(employees::add);

        if(employees.size() == 0)
        {
            response.setOperationStatus(Status.Failure,"No such Employee found!!");

        }else {
            response.setOperationStatus(Status.Success,employees);
        }

        return response;
    }


    public Response deleteEmployee(long eId)
    {
        Response response = new Response();

        if(employeeRespository.existsByExternalId(eId))
        {
            Employee e  = employeeRespository.deleteEmployeeByExternalId(eId);
            if(departmentService.deleteEmployeeInDepartment(e.getExternalId(),e.getDepartmentId())){
                response.setOperationStatus(Status.Success,e);
            }
            else{
                employeeRespository.save(e);
                response.setOperationStatus(Status.Failure,"Cannot remove Employee from the mentioned department!");
            }

        }else{
            response.setOperationStatus(Status.Failure,"Employee with the external Id" + eId + " does not exists");
        }
        return response;
    }

    public Response updateEmployee(Employee employee)
    {
        Response response = new Response();

        if(employeeRespository.existsByExternalId(employee.getExternalId()))
        {
            Employee e = employeeRespository.findEmployeeByExternalId(employee.getExternalId());
            if(!departmentService.transferEmployee(employee.getDepartmentId(),e))
            {
                response.setOperationStatus(Status.Failure,"Cannot transfer the department!");
            }else {
                e.setEmail(employee.getEmail());
                e.setFirstName(employee.getFirstName());
                e.setAddress(employee.getAddress());
                e.setContactNumber(employee.getContactNumber());
                e.setLastName(employee.getLastName());
                response.setOperationStatus(Status.Success, employeeRespository.save(e));
            }
        }
        else {
            response.setOperationStatus(Status.Failure,"No such employee found!!");
        }

        return response;
    }






    //

    public Employee getEmployeeByExternalId(long eId)
    {
        return employeeRespository.findEmployeeByExternalId(eId);
    }

    public boolean existsEmployee(long eId)
    {
        return employeeRespository.existsByExternalId(eId);
    }

    public Employee saveEmployee(Employee e)
    {
        return  employeeRespository.save(e);
    }

}
