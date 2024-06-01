import com.fasterxml.jackson.databind.ObjectMapper;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.models.Colour;
import org.fishFromSanDiego.cats.SpringBootCatsApplication;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringBootCatsApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApplicationTests {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;

    @Test
    public void usersAreRegisteredCorrectly() throws Exception {
        for (int i = 0; i < 10; ++i)
            mockMvc.perform(post("/users").content(asJsonString(UserDto.builder()
                            .firstName(String.format("John %d", i))
                            .secondName("Doe")
                            .build())).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        String result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONArray array = new JSONArray(result);
        Assertions.assertEquals(10, array.length());
        for (int i = 0; i < 10; ++i) {
            var user = getUserById(i + 1);
            Assertions.assertEquals(i + 1, user.getId());
            Assertions.assertEquals(String.format("John %d", i), user.getFirstName());
            Assertions.assertEquals("Doe", user.getSecondName());
            Assertions.assertNull(user.getBirthDate());
        }
    }

    @Test
    public void usersAreRemovedCorrectly() throws Exception {
        var user1 = registerDefaultUser();
        var user2 = registerDefaultUser();

        mockMvc.perform(delete("/users/112")).andExpect(status().isNotFound());
        mockMvc.perform(delete("/users/1")).andExpect(status().isOk());

        String result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONArray array = new JSONArray(result);
        Assertions.assertEquals(1, array.length());
        Assertions.assertEquals(2, array.getJSONObject(0).opt("id"));
    }

    @Test
    public void userPropertiesChangedCorrectly() throws Exception {
        UserDto user = registerDefaultUser();
        var userToPutBuilder = UserDto.builder()
                .birthDate(LocalDate.from(LocalDate.EPOCH))
                .firstName("Java")
                .secondName("Java");
        mockMvc.perform(put("/users/1").content(
                        "{\"firstName\": \"Java\",\"secondName\": \"Java\",\"birthDate\": \"01-01-1970\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        UserDto putUser = getUserById(1);
        userToPutBuilder.id(1);
        UserDto expectedUser = userToPutBuilder.build();
        Assertions.assertEquals(expectedUser, putUser);
    }

    @Test
    public void catPropertiesChangedCorrectly() throws Exception {
        UserDto user = registerDefaultUser();
        var cat = defaultCat("Ayana");
        cat.setOwnerId(1);
        mockMvc.perform(post("/cats").content(asJsonString(cat))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        cat.setName("Morgen");
        cat.setBreed("idk");
        cat.setBirthDate(LocalDate.EPOCH);
        cat.setColour(Colour.CHOCOLATE);
        mockMvc.perform(put("/cats/1").content(asJsonString(cat))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        var putCat = getCatById(1);
        cat.setId(1);

        Assertions.assertEquals(cat, putCat);
    }


    @Test
    public void friendOpsAreCorrect() throws Exception {
        registerDefaultUser();
        registerDefaultUser();
        for (int i = 0; i < 3; ++i) {
            var cat = defaultCat("kitty");
            cat.setOwnerId(1);
            registerCat(cat);
        }
        for (int i = 0; i < 3; ++i) {
            var cat = defaultCat("kitty");
            cat.setOwnerId(2);
            registerCat(cat);
        }
        friendCats(1, 2);
        friendCats(1, 3);
        friendCats(1, 4);
        friendCats(1, 5);
        friendCats(4, 1);
        friendCats(6, 1);

        JSONArray incomingInvites = new JSONArray(mockMvc.perform(get("/cats/1/friends?friendshipType=incoming"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        JSONArray outgoingInvites = new JSONArray(mockMvc.perform(get("/cats/1/friends?friendshipType=outgoing"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        JSONArray mutualFriends = new JSONArray(mockMvc.perform(get("/cats/1/friends"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());

        mockMvc.perform(post("/cats/1/friends").content("{\"friendId\" : 1}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

        Assertions.assertEquals(3, mutualFriends.length());
        unfriendCats(1, 3);

        mutualFriends = new JSONArray(mockMvc.perform(get("/cats/1/friends"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());

        Assertions.assertEquals(2, mutualFriends.length());
        Assertions.assertEquals(1, incomingInvites.length());
        Assertions.assertEquals(1, outgoingInvites.length());
        Assertions.assertEquals(5, outgoingInvites.getJSONObject(0).opt("id"));
        Assertions.assertEquals(6, incomingInvites.getJSONObject(0).opt("id"));
    }

    @Test
    public void removedUserscatsAreRemovedCorrectly() throws Exception {
        registerDefaultUser();
        registerDefaultUser();

        var cat1 = defaultCat("Maxwell");
        var cat2 = defaultCat("Gurenyaaa");
        cat1.setOwnerId(1);
        cat2.setOwnerId(1);
        registerCat(cat1);
        registerCat(cat2);

        var cat3 = defaultCat("a");
        cat3.setOwnerId(2);
        registerCat(cat3);

        mockMvc.perform(delete("/users/1")).andExpect(status().isOk());
        String result = mockMvc.perform(get("/cats"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONArray array = new JSONArray(result);

        Assertions.assertEquals(1, array.length());
    }

    private UserDto registerDefaultUser() throws Exception {
        return fromJsonString(mockMvc.perform(post("/users").content(asJsonString(UserDto.builder()
                        .firstName("John")
                        .secondName("Doe")
                        .build())).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), UserDto.class);
    }

    private CatDto registerCat(CatDto cat) throws Exception {
        return fromJsonString(mockMvc.perform(post("/cats").content(asJsonString(cat))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), CatDto.class);
    }

    private UserDto getUserById(long id) throws Exception {
        return fromJsonString(mockMvc.perform(get("/users/" + (id)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), UserDto.class);
    }

    private CatDto getCatById(long id) throws Exception {
        return fromJsonString(mockMvc.perform(get("/cats/" + (id)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), CatDto.class);
    }

    public static CatDto defaultCat(String name) {
        return CatDto.builder().name(name).colour(Colour.GREY).birthDate(LocalDate.now()).build();
    }

    private void friendCats(long catId, long friendId) throws Exception {
        mockMvc.perform(post("/cats/" + catId + "/friends").content(String.format("{\"friendId\" : %d}", friendId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    private void unfriendCats(long catId, long friendId) throws Exception {
        mockMvc.perform(delete("/cats/" + catId + "/friends").content(String.format("{\"friendId\" : %d}", friendId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    public String asJsonString(final Object obj) {
        try {
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJsonString(final String jsonString, Class<T> clazz) {
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
