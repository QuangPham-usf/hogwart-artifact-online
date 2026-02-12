package edu.usf.cs.hogwart_artifact_online.artifact;

import edu.usf.cs.hogwart_artifact_online.system.StatusCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ArtifactControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    ArtifactService artifactService;

    List<Artifact> artifacts;
    @BeforeEach
    void setUp() {
        this.artifacts = new ArrayList<>();
        // Artifact 4: Marauder's Map
        Artifact a4 = new Artifact();
        a4.setId("1250808601744904194");
        a4.setName("Marauder's Map");
        a4.setDesciption("A magical document that reveals all of Hogwarts School of Witchcraft and Wizardry.");
        a4.setImageUrl("ImageUrl");
        this.artifacts.add(a4);

// Artifact 5: Resurrection Stone
        Artifact a5 = new Artifact();
        a5.setId("1250808601744904195");
        a5.setName("Resurrection Stone");
        a5.setDesciption("One of the Deathly Hallows, said to have the power to recall loved ones from the dead.");
        a5.setImageUrl("ImageUrl");
        this.artifacts.add(a5);

// Artifact 6: Sword of Gryffindor
        Artifact a6 = new Artifact();
        a6.setId("1250808601744904196");
        a6.setName("Sword of Gryffindor");
        a6.setDesciption("A thousand-year-old goblin-made sword that once belonged to Godric Gryffindor.");
        a6.setImageUrl("ImageUrl");
        this.artifacts.add(a6);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testfindArtifactByIdSuccess() throws Exception {
        //Given
        given(this.artifactService.findById("1250808601744904196")).willReturn(this.artifacts.get(2));

        //When
        this.mockMvc.perform(get("/findArtifact/1250808601744904196")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("1250808601744904196"))
                .andExpect(jsonPath("$.data.name").value("Sword of Gryffindor"));


    }
    }