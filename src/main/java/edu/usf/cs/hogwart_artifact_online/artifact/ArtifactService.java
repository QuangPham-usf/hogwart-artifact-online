package edu.usf.cs.hogwart_artifact_online.artifact;

import edu.usf.cs.hogwart_artifact_online.artifact.B2B.ArtDtotoOriginal;
import edu.usf.cs.hogwart_artifact_online.artifact.converter.ArtifactDtoConverter;
import edu.usf.cs.hogwart_artifact_online.artifact.dto.ArtifactDto;
import edu.usf.cs.hogwart_artifact_online.artifact.util.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArtifactService {
    // select class name -> choose test.. + setup tear down
    private final ArtifactRepo artifactRepo;
    private final ArtifactDtoConverter artifactDtoConverter;
    private final IdWorker idWorker;
    private final ArtDtotoOriginal artDtotoOriginal;

    public ArtifactService(ArtifactRepo artifactRepo, ArtifactDtoConverter artifactDtoConverter, IdWorker idWorker, ArtDtotoOriginal artDtotoOriginal) {
        this.artifactRepo = artifactRepo;
        this.artifactDtoConverter = artifactDtoConverter;
        this.idWorker = idWorker;
        this.artDtotoOriginal = artDtotoOriginal;
    }


    public ArtifactDto findById(String id) {
        Artifact q1 = this.artifactRepo.findById(id).orElseThrow(() -> new ArtifactNotFoundException(id));
        // inside throw is funtional interface, so we implement by lambda, get() belong to supplie, it return value
        //suppplie<T> T is output type, require no input
        return artifactDtoConverter.convert(q1);
    }


    public List<ArtifactDto> findAll() {
        List<Artifact> q1 = artifactRepo.findAll();
        /*
         @FunctionalInterface
public interface Function<T, R> { take t as input, R as output
    R apply(T t);  Here is what inside map
         */
        List<ArtifactDto> ans = q1.stream().
                map(o1 -> artifactDtoConverter.convert(o1)).collect(Collectors.toList());
        return ans;
    }

    public Artifact save(Artifact artifact) {
        artifact.setId(idWorker.nextId() + "");
        return this.artifactRepo.save(artifact);
    }
    public Artifact update(String Id, Artifact update){
        Artifact oldOne = this.artifactRepo.findById(Id).orElseThrow(() -> new ArtifactNotFoundException(Id));
        oldOne.setName((update.getName()));
        oldOne.setImageUrl(update.getImageUrl());
        oldOne.setDesciption(update.getDesciption());
        Artifact newOne = artifactRepo.save(oldOne);// if there is an ID,save() will just modify else add()

        return newOne;
    }
    public void delete(String Id) {
        Artifact artifact = this.artifactRepo.findById(Id).orElseThrow(() -> new ArtifactNotFoundException(Id));
        this.artifactRepo.deleteById(Id);
    }
}






/*
@Component
class FunctionDto implements Function<Artifact, ArtifactDto> {
    private final ArtifactDtoConverter artifactDtoConverter;

    public FunctionDto(ArtifactDtoConverter artifactDtoConverter) {
        this.artifactDtoConverter = artifactDtoConverter;
    }

    @Override
    public ArtifactDto apply(Artifact artifact) {
        return artifactDtoConverter.convert(artifact);
    }
}
 */