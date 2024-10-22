package kg.amanturov.forum.service;



import kg.amanturov.forum.dto.EmployeeSIgnUpDto;
import kg.amanturov.forum.dto.securityDto.LoginResponse;

import java.util.List;

public interface SignUpService {

    void saveEmployee(EmployeeSIgnUpDto dtos);

    LoginResponse findByUserName(String userName);
}
