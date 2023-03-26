package com.Me.ShiftBoard.Controllers;

import com.Me.ShiftBoard.Model.Department;
import com.Me.ShiftBoard.Model.Employee;
import com.Me.ShiftBoard.Service.DepartmentService;
import com.Me.ShiftBoard.UtilityClasses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/{departmentId}")
    public Response getDepartmentById(@PathVariable long departmentId)
    {
        return departmentService.findDepartmentById(departmentId);
    }

    @GetMapping("/search")
    public Response getDepartmentBySearch(@RequestParam Map<String ,String> params)
    {
        return departmentService.getDepartmentsBySearch(params);
    }

    @GetMapping("")
    public Response getAllDepartments()
    {
        return departmentService.getAllDepartments();
    }

    @PostMapping("")
    public Response addDepartment(@RequestBody Department department)
    {
        return departmentService.addDepartment(department);
    }

    @PutMapping("")
    public Response updateDepartment(@RequestBody Department department)
    {
       return departmentService.updateDepartment(department);
    }

    @DeleteMapping("/{departmentId}")
    public Response deleteDepartment(@PathVariable long departmentId)
    {
        return departmentService.deleteDepartment(departmentId);
    }


}
