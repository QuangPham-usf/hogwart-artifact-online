package edu.usf.cs.hogwart_artifact_online.artifact;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import edu.usf.cs.hogwart_artifact_online.Wizard.Wizard;
import jakarta.persistence.JoinColumn;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)//  remember to export Mockito mandatory
class ArtifactServiceTest {
    @Mock
    ArtifactRepo artifactRepo;// this is the object we want to simulate,
    @InjectMocks// inject repo to service on test
    ArtifactService artifactService;
    // because service depends on repo, repo is dependency -> we need to create a mock of repo for service working
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        //Given.Arrange input and target
        Artifact artifact = new Artifact();
        artifact.setId("6789");
        artifact.setName("dua");
        artifact.setDesciption("magic");
        artifact.setImageUrl("url");

        Wizard wizard = new Wizard();
        wizard.setId(2);
        wizard.setName("qp");

        artifact.setOwner(wizard);
        // given... define what reposetory do when being call (mocking)
        given(artifactRepo.findById("6789")).willReturn(Optional.of(artifact));

        //when
        Artifact returnedArtifact = artifactService.findById("6789");

        // Then
        assertThat(returnedArtifact.getId()).isEqualTo(artifact.getId());
        assertThat(returnedArtifact.getName()).isEqualTo(artifact.getName());
        assertThat(returnedArtifact.getImageUrl()).isEqualTo(artifact.getImageUrl());
        assertThat(returnedArtifact.getDesciption()).isEqualTo(artifact.getDesciption());
        verify(artifactRepo, times(1)).findById("6789");

    }
    @Test
        void testFindByIdNotFound() {
        // Given
            given(artifactRepo.findById(Mockito.any(String.class))).willReturn(Optional.empty());
        //when
            Throwable thrown_var = catchThrowable(() -> {
                Artifact returnedArtifact = artifactService.findById("6789");
            });
            // then
        assertThat(thrown_var)
                .isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could not find object with Id 6789");
        verify(artifactRepo, times(1)).findById("6789");

    }


}