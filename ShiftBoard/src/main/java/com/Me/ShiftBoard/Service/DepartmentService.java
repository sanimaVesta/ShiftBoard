package com.Me.ShiftBoard.Service;

import com.Me.ShiftBoard.Model.Department;
import com.Me.ShiftBoard.Model.Employee;
import com.Me.ShiftBoard.Repository.DepartmentRepository;
import com.Me.ShiftBoard.UtilityClasses.Response;
import com.Me.ShiftBoard.UtilityClasses.Status;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {


    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;


    public boolean transferEmployee(Long newDept, Employee tmp)
    {
        List<Long> eIds;

        if(departmentRepository.existsDepartmentByDepartmentId(newDept))
        {
                Department newDepartment = departmentRepository.findDepartmentByDepartmentId(newDept);

                Department oldDepartment = departmentRepository.findDepartmentByDepartmentId(tmp.getDepartmentId());
                oldDepartment.setNoOfEmployees(oldDepartment.getNoOfEmployees()-1);
                eIds = oldDepartment.getEmployeesId();
                eIds.remove(tmp.getExternalId());
                oldDepartment.setEmployeesId(eIds);
                departmentRepository.save(oldDepartment);

                newDepartment.setNoOfEmployees(newDepartment.getNoOfEmployees() + 1);
                eIds = newDepartment.getEmployeesId();
                eIds.add(tmp.getExternalId());
                newDepartment.setEmployeesId(eIds);

               departmentRepository.save(newDepartment);

               tmp.setDepartmentId(newDept);
               employeeService.saveEmployee(tmp);

               return true;
            }
        else {
            return false;
        }

    }


    public boolean addEmployeeInDepartment(long externalId,long departmentId) {

        if(departmentRepository.existsDepartmentByDepartmentId(departmentId))
        {
                Department tmp = departmentRepository.findDepartmentByDepartmentId(departmentId);
                List<Long> eIds = tmp.getEmployeesId();
                eIds.add(externalId);
                tmp.setEmployeesId(eIds);
                tmp.setNoOfEmployees(tmp.getNoOfEmployees() + 1);
                departmentRepository.save(tmp);
                return true;
        }
        return  false;
    }

    public boolean deleteEmployeeInDepartment(long externalId, long departmentId)
    {
        if(departmentRepository.existsDepartmentByDepartmentId(departmentId)) {
            Department tmp = departmentRepository.findDepartmentByDepartmentId(departmentId);
            List<Long> eIds = tmp.getEmployeesId();
            eIds.remove(externalId);
            tmp.setEmployeesId(eIds);
            tmp.setNoOfEmployees(tmp.getNoOfEmployees() - 1);
            departmentRepository.save(tmp);
            return true;
        }

        return false;
    }


    public Response findDepartmentById(long departmentId)
    {   Response response = new Response();
        if(departmentRepository.existsDepartmentByDepartmentId(departmentId))
        {   response.setOperationStatus(Status.Success,departmentRepository.findDepartmentByDepartmentId(departmentId));
        }else{
            response.setOperationStatus(Status.Failure,"No such Department found!");
        }

        return response;
    }

    public Response getAllDepartments() {

        Response response = new Response();

        response.setOperationStatus(Status.Success,departmentRepository.findAll());
        return  response;
    }

    public Response addDepartment(Department department) {
        Response response = new Response();
        List<Long> employeeIds = department.getEmployeesId();
        department.setEmployeesId(new ArrayList<>());
        department.setNoOfEmployees(0);
        long dId = department.getDepartmentId();
        if(departmentRepository.existsDepartmentByDepartmentId(dId))
        {
            response.setOperationStatus(Status.Failure,"Department with the given id already exists!!");
        }
        else {
            department.setId(sequenceGeneratorService.generateSequence(Department.SEQUENCE_NAME));
            departmentRepository.save(department);

            for (long eId: employeeIds) {
                if(employeeService.existsEmployee(eId))
                {
                    Employee e = employeeService.getEmployeeByExternalId(eId);
                    transferEmployee(dId,e);
                }
                else{
                    response.setWarning("Some employee does not exists");
                }
            }

            response.setOperationStatus(Status.Success,departmentRepository.findDepartmentByDepartmentId(dId));
        }
        return response;

    }

    public Response updateDepartment(Department department) {

        Response response = new Response();

        if(departmentRepository.existsDepartmentByDepartmentId(department.getDepartmentId()))
        {
            Department tmp = departmentRepository.findDepartmentByDepartmentId(department.getDepartmentId());

            tmp.setEmail(department.getEmail());
            tmp.setName(department.getName());
            departmentRepository.save(tmp);
            response.setOperationStatus(Status.Success,tmp);
        }else {
            response.setOperationStatus(Status.Failure,"No such department exists!1");
        }

        return response;
    }

    public Response deleteDepartment(long departmentId) {
        Response response = new Response();

        if(departmentRepository.existsDepartmentByDepartmentId(departmentId))
        {
            Department tmp = departmentRepository.deleteDepartmentByDepartmentId(departmentId);

            tmp.getEmployeesId().forEach(eId -> {
                Employee e = employeeService.getEmployeeByExternalId(eId);
               transferEmployee((long) -1,e);
                employeeService.saveEmployee(e);
            });

            response.setOperationStatus(Status.Success,tmp);
        }
        else{
            response.setOperationStatus(Status.Failure,"No such department exists!");
        }
    return response;
    }

    public Response getDepartmentsBySearch(Map<String, String> params) {

        Response response = new Response();

        BooleanBuilder builder = DepartmentPredicateBuilder.getSearchPredicates(params);

        List<Department> departments = new ArrayList<>();


       departmentRepository.findAll(builder).iterator().forEachRemaining(departments::add);

        if(departments.size() == 0)
        {
            response.setOperationStatus(Status.Failure,"No such Department found!!");

        }else {
            response.setOperationStatus(Status.Success,departments);
        }

        return response;


    }
}
