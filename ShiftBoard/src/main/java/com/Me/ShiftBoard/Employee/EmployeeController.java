package com.Me.ShiftBoard.Employee;

import com.Me.ShiftBoard.Generator;
import com.Me.ShiftBoard.Util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {


    private  final EmployeeService employeeService;


   @PostMapping("/admin")
    public ResponseEntity<Response> addEmployee(@RequestBody Employee employee)
   {

       return ResponseEntity.ok(employeeService.addEmployee(employee));
   }


   //client and admin
   @PutMapping("")
   public ResponseEntity<Response>  updateEmployee(@RequestBody Employee employee)
   {
        return ResponseEntity.ok(employeeService.updateEmployee(employee));
   }

   @PutMapping("/admin/transfer")
   public ResponseEntity<Response>  transferEmployee(@RequestBody Employee employee)
   {
       return ResponseEntity.ok(employeeService.transferEmployee(employee));
   }

   @DeleteMapping("/admin/{externalId}")
    public ResponseEntity<Response>  deleteEmployee(@PathVariable("externalId") long eId)
   {
       return ResponseEntity.ok(employeeService.deleteEmployee(eId));
   }

   // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Response>  getEmployees()
    {

        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    //client and admin
    @GetMapping("/{externalId}")
    public ResponseEntity<Response>  getEmployeeByeId(@PathVariable("externalId") long eId)
    {
        return ResponseEntity.ok(employeeService.getEmployee(eId));
    }

    @GetMapping("/admin/search")
    public ResponseEntity<Response>  getEmployeeBySearch(@RequestParam Map<String, String> params)
    {
        return  ResponseEntity.ok(employeeService.getEmployeesBySearch(params));
    }

}
