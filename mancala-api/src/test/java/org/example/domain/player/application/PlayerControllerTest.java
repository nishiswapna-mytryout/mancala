package org.example.domain.player.application;

import org.example.domain.player.core.model.command.AddPlayerCommand;
import org.example.domain.player.core.model.command.GetPlayerCommand;
import org.example.domain.player.core.model.output.PlayerIdentifier;
import org.example.domain.player.core.model.output.PlayerResponse;
import org.example.domain.player.core.ports.incoming.AddNewPlayer;
import org.example.domain.player.core.ports.incoming.GetPlayer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server.port=0")
@DirtiesContext
public class PlayerControllerTest extends ControllerTest {

    @MockBean
    private AddNewPlayer addNewPlayer;
    @MockBean
    private GetPlayer getPlayer;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPlayerCreation() throws Exception {

        Mockito.when(this.addNewPlayer.add(any(AddPlayerCommand.class)))
                .thenReturn(new PlayerIdentifier("SomeValue"));

        this.mockMvc.perform(post("/player")
                .content(asJsonString(new AddPlayerCommand("firstName4", "lastName4")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.playerId").value("SomeValue"))
                .andReturn();
    }

    @Test
    public void testPlayerCreationWhenErrorFromFacade() throws Exception {

        Mockito.when(this.addNewPlayer.add(any(AddPlayerCommand.class)))
                .thenThrow(new IllegalArgumentException("Some Error happened"));

        this.mockMvc.perform(post("/player")
                .content(asJsonString(new AddPlayerCommand("firstName4", "lastName4")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errorTime").exists())
                .andReturn();
    }

    @Test
    public void testGetPlayerWhenExistingPlayerId() throws Exception {

        Mockito.when(this.getPlayer.get(any(GetPlayerCommand.class))).thenReturn(new PlayerResponse("somevalue","somevalue"));

        this.mockMvc.perform(get("/player/{id}","id8475785")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andReturn();

    }

    @Test
    public void testGetPlayerWhenNonExistingPlayerId() throws Exception {

        Mockito.when(this.getPlayer.get(any(GetPlayerCommand.class)))
                .thenReturn(null);

        this.mockMvc.perform(get("/player/{id}","tttttt")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    public void testGetPlayerWhenErrorThrown() throws Exception {

        Mockito.when(this.getPlayer.get(any(GetPlayerCommand.class)))
                .thenThrow(new IllegalArgumentException("Invalid player"));

        this.mockMvc.perform(get("/player/{id}","tttttt")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errorTime").exists())
                .andReturn();

    }


}