package edu.usf.cs.hogwart_artifact_online.artifact;

import edu.usf.cs.hogwart_artifact_online.artifact.B2B.ArtDtotoOriginal;
import edu.usf.cs.hogwart_artifact_online.artifact.converter.ArtifactDtoConverter;
import edu.usf.cs.hogwart_artifact_online.artifact.dto.ArtifactDto;
import edu.usf.cs.hogwart_artifact_online.system.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// remember infinite recursion of one to many relationship
// jackson just read in controller, at input param  and return
@RestController
@RequestMapping("/api/v1/artifacts")
public class ArtifactController {
    private final ArtifactService artifactService;
    private final ArtDtotoOriginal artDtotoOriginal;
    private final ArtifactDtoConverter artifactDtoConverter;

    public ArtifactController(ArtifactService artifactService, ArtDtotoOriginal artDtotoOriginal, ArtifactDtoConverter artifactDtoConverter) {
        this.artifactService = artifactService;
        this.artDtotoOriginal = artDtotoOriginal;
        this.artifactDtoConverter = artifactDtoConverter;
    }


    @GetMapping("/{Id}")// name should match parameter
    public Result findArtifactById(@PathVariable String Id) {
        // 1. Use the service to find the data
        ArtifactDto artifactDto = artifactService.findById(Id);
        // 2. Wrap it in your Result object (Success code, Message, and the Data)
        return new Result(true, 200, "Find One Success", artifactDto);
    }


    @GetMapping
    public Result findAllArtifact() {
        List<ArtifactDto> l1 = artifactService.findAll();
        return new Result(true, 200, "Find One Success", l1);
    }

    @PostMapping // Note: Typically @PostMapping uses "/api/v1/artifacts"
    public Result addArtifact(@Valid @RequestBody ArtifactDto artifactDto) {
//valid -> back to check dto if the field that go @notEmpty empty or not
        Artifact newArtifact = this.artDtotoOriginal.convert(artifactDto);

        Artifact savedArtifact = this.artifactService.save(newArtifact);

        ArtifactDto q1 = this.artifactDtoConverter.convert(savedArtifact);

        return new Result(true, 200, "Add Success", q1);
    }

    @PutMapping("/{Id}")
    public Result updateArtifact(@PathVariable String Id,@Valid @RequestBody ArtifactDto artifactDto) {
        Artifact original = this.artDtotoOriginal.convert(artifactDto);
        Artifact newOne = artifactService.update(Id,original);
        ArtifactDto q1 = this.artifactDtoConverter.convert(newOne);
        return new Result(true, 200, "Update Success", q1);
    }

    @DeleteMapping("/{Id}")
    public Result deleteArtifact(@PathVariable String Id) {
        artifactService.delete(Id);
        return new Result(true, 200, "Delete Success");
    }

    @PutMapping("/{wizardId}/{artifactId}")
    public Result assignArtifact(@PathVariable Integer wizardId, @PathVariable String artifactId) {
        artifactService.assignArtifact(artifactId,wizardId  );
        return new Result(true, 200, "Assign Success");
    }
}
 /*
    @FunctionalInterface
public interface Function<T, R> { take t as input, R as output
    R apply(T t);

    // 1. Full class
class MySupplier implements Supplier<Integer> {
    @Override
    public Integer get() {
        return 42;
    }
}

Supplier<Integer> numberSupplier = new MySupplier();
System.out.println(numberSupplier.get());  // 42


}

  */