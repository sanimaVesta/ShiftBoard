package com.Me.ShiftBoard.Controllers;

import com.Me.ShiftBoard.Models.Leave;
import com.Me.ShiftBoard.Services.Leave.LeaveService;
import com.Me.ShiftBoard.UtilityClasses.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaves")
public class LeaveController {

    private final LeaveService leaveService;


    @GetMapping("")
    public Response getAllLeaves()
    {
        return leaveService.getAllLeaves();
    }

    @PostMapping("")
    public Response addLeave(@RequestBody Leave leave)
    {
        return leaveService.addLeave(leave);
    }

    @GetMapping("/{employeeId}")
    public Response getLeaveByEmployee(@PathVariable long employeeId)
    {
        return leaveService.getLeaveByEmployee(employeeId);
    }

    @PutMapping("")
    public Response updateLeave(@RequestBody Leave leave)
    {
        return  leaveService.updateLeave(leave);
    }

    @DeleteMapping("/{leaveId}")
    public Response deleteLeave(@PathVariable long leaveId)
    {
        return  leaveService.deleteLeave(leaveId);
    }

    @GetMapping("/search")
    public Response getLeavesBySearch(@RequestParam Map<String, String> params)
    {
        return leaveService.getLeavesBySearch(params);
    }

}
