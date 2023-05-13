package com.Me.ShiftBoard.Department;

import com.Me.ShiftBoard.Generator;
import com.Me.ShiftBoard.Util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/departments/admin")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/{departmentId}")
    public ResponseEntity<Response> getDepartmentById(@PathVariable long departmentId)
    {
        return ResponseEntity.ok(departmentService.findDepartmentById(departmentId));
    }

    @GetMapping("/search")
    public ResponseEntity<Response> getDepartmentBySearch(@RequestParam Map<String ,String> params)
    {
        return ResponseEntity.ok(departmentService.getDepartmentsBySearch(params));
    }

    @GetMapping("")
    public ResponseEntity<Response> getAllDepartments()
    {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PostMapping("")
    public ResponseEntity<Response> addDepartment(@RequestBody Department department)
    {
        return ResponseEntity.ok(departmentService.addDepartment(department));
    }

    @PutMapping("")
    public ResponseEntity<Response> updateDepartment(@RequestBody Department department)
    {
       return ResponseEntity.ok(departmentService.updateDepartment(department));
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Response> deleteDepartment(@PathVariable long departmentId)
    {
        return ResponseEntity.ok(departmentService.deleteDepartment(departmentId));
    }

    @GetMapping("/employees/{departmentId}")
    public ResponseEntity<Response> getEmployeesByDepartmentId(@PathVariable long departmentId)
    {
        return ResponseEntity.ok(departmentService.getEmployeesByDepartmentId(departmentId));
    }

}
