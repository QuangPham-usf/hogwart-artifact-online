package edu.usf.cs.hogwart_artifact_online.User;

import edu.usf.cs.hogwart_artifact_online.User.Converter.DtoToUser;
import edu.usf.cs.hogwart_artifact_online.User.Converter.UserDtoConverter;
import edu.usf.cs.hogwart_artifact_online.User.UserDto.UserDto;
import edu.usf.cs.hogwart_artifact_online.Wizard.Wizard;
import edu.usf.cs.hogwart_artifact_online.Wizard.dto.WizardDto;
import edu.usf.cs.hogwart_artifact_online.system.Result;
import edu.usf.cs.hogwart_artifact_online.system.StatusCode;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/*
Valid tell Spring to check the object before it goes into the function, if it is not valid, it will throw an exception
- Request Body tell Spring to get the data from the body of the request, and convert it to the object we want
 */
@RestController
@RequestMapping("/api/v1/users")

public class UserController {
    private final UserService userService;
    /*
    We dont want a PASSWORD  in json return -> need a dto to hide that
     */
    private final UserDtoConverter userDtoConverter;
    private final DtoToUser dtoToUser;

    public UserController(UserService userService, UserDtoConverter userDtoConverter, DtoToUser dtoToUser) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.dtoToUser = dtoToUser;
    }


    @GetMapping("/{Id}")
    public Result putUser(Integer Id) {
        Users user = userService.findById(Id);
        UserDto userDto = userDtoConverter.convert(user);
        return new Result(true, StatusCode.SUCCESS, "Find User Success", userDto);
    }
    @GetMapping
    public Result findALl() {
        List<UserDto> q1 =  userService.findAll().stream().map(userDtoConverter::convert)// it expect a interface, but you pass funtion
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All User Success", q1);
    }
    @PostMapping
    public Result saveUser(@Valid @RequestBody Users user) {
        Users userSave = userService.save(user);

        UserDto finalOne = userDtoConverter.convert(userSave); //Convert to DTO to hide password
        return new Result(true, StatusCode.SUCCESS, "Save User Success", finalOne);
    }

    @PutMapping("/{Id}")
    public Result updateWizard(@PathVariable Integer Id, @Valid @RequestBody UserDto wizardDto) { // No password update here, need a separate function, and check the old password first
        Users para = dtoToUser.convert(wizardDto);
        Users updated = userService.update(Id,para);
        UserDto ans = userDtoConverter.convert(updated);
        return new Result(true, StatusCode.SUCCESS, "Update User Success", ans);
    }

    @DeleteMapping("/{Id}")
    public Result deleteWizard(@PathVariable Integer Id){
        userService.delete(Id);
        return new Result(true, StatusCode.SUCCESS, "Delete User Success");
    }


}
