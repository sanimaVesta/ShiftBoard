package com.Me.ShiftBoard.Controllers;

import com.Me.ShiftBoard.Model.Employee;
import com.Me.ShiftBoard.Repository.EmployeeRespository;
import com.Me.ShiftBoard.Service.EmployeeService;
import com.Me.ShiftBoard.Service.SequenceGeneratorService;
import com.Me.ShiftBoard.UtilityClasses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


   @PostMapping("")
    public Response addEmployee(@RequestBody Employee employee)
   {
       return employeeService.addEmployee(employee);
   }

   @PutMapping("")
   public Response updateEmployee(@RequestBody Employee employee)
   {
        return employeeService.updateEmployee(employee);
   }

   @DeleteMapping("/{externalId}")
    public Response deleteEmployee(@PathVariable("externalId") long eId)
   {
       return employeeService.deleteEmployee(eId);
   }

    @GetMapping("")
    public Response getEmployees()
    {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{externalId}")
    public Response getEmployeeByeId(@PathVariable("externalId") long eId)
    {
        return employeeService.getEmployee(eId);
    }

    @GetMapping("/search")
    public Response getEmployeeBySearch(@RequestParam Map<String, String> params)
    {
        return employeeService.getEmployeesBySearch(params);
    }


}
