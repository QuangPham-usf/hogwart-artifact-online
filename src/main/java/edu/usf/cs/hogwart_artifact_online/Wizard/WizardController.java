package edu.usf.cs.hogwart_artifact_online.Wizard;

import edu.usf.cs.hogwart_artifact_online.Wizard.converter.DtoToWizard;
import edu.usf.cs.hogwart_artifact_online.Wizard.converter.WizardDtoconverter;
import edu.usf.cs.hogwart_artifact_online.Wizard.dto.WizardDto;
import edu.usf.cs.hogwart_artifact_online.artifact.ArtifactService;
import edu.usf.cs.hogwart_artifact_online.system.Result;
import edu.usf.cs.hogwart_artifact_online.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/wizards")
public class WizardController {
    private final WizardDtoconverter wizardDtoconverter;
    private final WizardService wizardService;
    private final DtoToWizard dtoToWizard;

    public WizardController(WizardDtoconverter wizardDtoconverter, WizardService wizardService, DtoToWizard dtoToWizard) {
        this.wizardDtoconverter = wizardDtoconverter;
        this.wizardService = wizardService;
        this.dtoToWizard = dtoToWizard;
    }


    /*
       public Result(boolean flag, Integer code, String message, Object data) {
            this.flag = flag;
            this.code = code;
            this.message = message;
            this.data = data;
        }
     */
    @GetMapping("/{Id}")
    public Result findWizardById(@PathVariable Integer Id) {
       Wizard wizard =  wizardService.findById(Id);
       WizardDto wizardDto = wizardDtoconverter.convert(wizard);
       return new Result(true, StatusCode.SUCCESS, "Find One Success", wizardDto);
    }

    @GetMapping
    public Result findALl() {
        List<WizardDto> q1=  wizardService.findAll().stream().map(wizardDtoconverter::convert)// it expect a interface, but you pass funtion
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", q1);
    }

    @PostMapping
    public Result saveWizard(@Valid @RequestBody WizardDto wizardDto) {
        Wizard wizard = dtoToWizard.convert(wizardDto);
        Wizard saveOneReturn = wizardService.save(wizard);
        WizardDto finalOne = wizardDtoconverter.convert(saveOneReturn);
        return new Result(true, StatusCode.SUCCESS, "Save Success", finalOne);
    }

    @PutMapping("/{Id}")
    public Result updateWizard(@PathVariable Integer Id, @Valid @RequestBody WizardDto wizardDto) {
        Wizard para = dtoToWizard.convert(wizardDto);
        Wizard q1 = wizardService.update(Id,para);
        WizardDto ans = wizardDtoconverter.convert(q1);
        return new Result(true, StatusCode.SUCCESS, "Update Success", ans);
    }

    @DeleteMapping("/{Id}")
    public Result deleteWizard(@PathVariable Integer Id){
        wizardService.delete(Id);
        return new Result(true, StatusCode.SUCCESS, "Update Success");
    }


}




/*
// This is the Interface being used manually
Function<Wizard, WizardDto> myLogic = new Function<Wizard, WizardDto>() {
    @Override
    public WizardDto apply(Wizard wizard) {
        return converter.convert(wizard);
    }
};

// You pass the interface object to map
wizards.stream().map(myLogic).toList();
 */