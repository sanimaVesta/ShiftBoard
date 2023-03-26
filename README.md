# ShiftBoard
Shift Management App

## Resources
### Employee
Get : /employees  -gets all the employees\
Get : /employees/{id}   -gets the employee with the employeeId as id\
Get : /employees/search?[params]   -gets all the employee which matches the criteria. Criteria is given as Eg./search?firstName=sahil&lastName=kalathiya\
Delete : /employees/{id}  -deletes the employee with the employerId as id\
Put : /employees  -- modifies the employer object given as a parameter in request body\
Post : /employees --adds the employee with parameters in the request body
### Department

Get : /departments  -gets all the departments\
Get : /departments/{id}   -gets the department with the departmentId as id\
Get : /departments/search?[params]   -gets all the department which matches the criteria. Criteria is given as Eg./search?name=Zara\
Delete : /departments/{id}  -deletes the department with the departmentId as id\
Put : /departments  -- modifies the department object given as a parameter in request body\
Post : /departments --adds the department with parameters in the request body
