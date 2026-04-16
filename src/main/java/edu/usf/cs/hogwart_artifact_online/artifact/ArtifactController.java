package edu.usf.cs.hogwart_artifact_online.artifact;

import edu.usf.cs.hogwart_artifact_online.artifact.B2B.ArtDtotoOriginal;
import edu.usf.cs.hogwart_artifact_online.artifact.converter.ArtifactDtoConverter;
import edu.usf.cs.hogwart_artifact_online.artifact.dto.ArtifactDto;
import edu.usf.cs.hogwart_artifact_online.system.Result;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
JSON -> object : deserialization
object -> JSON : serialization
 */
// remember infinite recursion of one to many relationship
// jackson just read in controller, in de/serialization process, at input(de)  and return(serialization)
@RestController
@RequestMapping("/api/v1/artifacts")
public class    ArtifactController {
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

/*
Pageable : Page number: at which page u standing at, Page size: how many data at that page, Sort: how to sort the data,a-z, price...asc, desc
 - If we have 1 million data, we dont want to return once , too lag
 - It provide useful info for frontend like total page, total element, current page, page size, is first page, is last page
 */
    @GetMapping
    public Result findAllArtifact(Pageable pageable) {
        Page<ArtifactDto> l1 = artifactService.findAll(pageable);
        return new Result(true, 200, "Find One Success", l1);
    }
    /*
    - In url : we have to pass Pagenumber, Page size, Sort  because -> new PageRequest(P, P ,S) is created
    - if not pass in the para, PageRequest will still be created butPageab; use default value, which is page number 0, page size 20, and no sort
    - Then PageableHandlerMethodArgumentResolver will call PageRequest.of(page, size, sort) to create a PageRequest object, and pass it to the function
    *** PageRequest extend interface Pageable
    - Letter sort by , khi nhập page -L nó tính offset = page * size và nó skip thật. vd page 2 * size 2 = 4, lấy từ 5 trở đi
     */

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
        artifactService.assignArtifact(artifactId,wizardId );
        return new Result(true, 200, "Assign Success");
    }
}
 /*
    @FunctionalInterface
public interface name_here<T, R> { take t as input, R as output
    R apply(T t);
public class UserToPrincipalConverter implements Function<Users, MyUserPrincipal> {
    @Override
    public MyUserPrincipal apply(Users user) {
        return new MyUserPrincipal(user);
    }

}

  */