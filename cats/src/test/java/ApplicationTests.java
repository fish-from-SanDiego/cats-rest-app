import com.fasterxml.jackson.databind.ObjectMapper;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.models.Colour;
import org.FishFromSanDiego.cats.models.CustomAppProperties;
import org.FishFromSanDiego.cats.security.WebSecurityConfig;
import org.fishFromSanDiego.cats.SpringBootCatsApplication;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {SpringBootCatsApplication.class, WebSecurityConfig.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ApplicationTests {

    protected MockMvc mockMvc;
    protected ObjectMapper mapper;

    protected CustomAppProperties appProperties;

    @Autowired
    public ApplicationTests(MockMvc mockMvc, ObjectMapper mapper, CustomAppProperties appProperties) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
        this.appProperties = appProperties;
    }


    @Test
    public void anonymousRequestsShouldNotPass() throws Exception {
        mockMvc.perform(get("/users")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/cats")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/user")).andExpect(status().isUnauthorized());
    }

    @Test
    public void onlyAdminCanRegisterUsers() throws Exception {
        registerNewDefaultUser("someUser", "password").andExpect(status().isOk());
        mockMvc
                .perform(post("/users")
                        .content(asJsonString(getDefaultUser().username("otherUser").password("password").build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(appUserHttpBasic("someUser", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void usernamesShouldBeUnique() throws Exception {
        registerNewDefaultUser("someUser", "password").andExpect(status().isOk());
        registerNewDefaultUser("someUser", "password123").andExpect(status().isConflict());
    }

    @Test
    public void userLoginIsCorrect() throws Exception {
        registerNewDefaultUser("someUser", "1234").andExpect(status().isOk());
        UserDto user = expectToObject(
                mockMvc.perform(get("/user").with(appUserHttpBasic("someUser", "1234"))).andExpect(status().isOk()),
                UserDto.class);
        Assertions.assertEquals("someUser", user.getUsername());
        Assertions.assertNull(user.getPassword()); // password should not be passed in response
    }

    @Test
    public void usersAreRegisteredCorrectly() throws Exception {
        for (int i = 0; i < 10; ++i)
            mockMvc
                    .perform(post("/users")
                            .content(asJsonString(UserDto
                                    .builder()
                                    .username(String.format("John %d", i))
                                    .password("password")
                                    .firstName(String.format("John %d", i))
                                    .secondName("Doe")
                                    .build()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .with(appUserHttpBasic(appProperties.getDefaultAdminUsername(),
                                    appProperties.getDefaultAdminPassword())))
                    .andExpect(status().isOk());

        String result = mockMvc
                .perform(get("/users").with(appUserHttpBasic(appProperties.getDefaultAdminUsername(),
                        appProperties.getDefaultAdminPassword())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONArray array = new JSONArray(result);
        Assertions.assertEquals(10, array.length());
        for (int i = 0; i < 10; ++i) {
            var user = expectToObject(getUserById(i + 2,
                    appProperties.getDefaultAdminUsername(),
                    appProperties.getDefaultAdminPassword()).andExpect(status().isOk()), UserDto.class);
            Assertions.assertEquals(i + 2, user.getId());
            Assertions.assertEquals(String.format("John %d", i), user.getFirstName());
            Assertions.assertEquals("Doe", user.getSecondName());
            Assertions.assertNull(user.getBirthDate());
        }
    }

    @Test
    public void usersAreRemovedCorrectly() throws Exception {
        registerNewDefaultUser("user1", "1234").andExpect(status().isOk());
        registerNewDefaultUser("user2", "12345").andExpect(status().isOk());

        mockMvc
                .perform(delete("/users/112").with(appUserHttpBasic(appProperties.getDefaultAdminUsername(),
                        appProperties.getDefaultAdminPassword())))
                .andExpect(status().isNotFound());
        mockMvc
                .perform(delete("/users/2").with(appUserHttpBasic(appProperties.getDefaultAdminUsername(),
                        appProperties.getDefaultAdminPassword())))
                .andExpect(status().isOk());

        String result = mockMvc
                .perform(get("/users").with(appUserHttpBasic(appProperties.getDefaultAdminUsername(),
                        appProperties.getDefaultAdminPassword())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JSONArray array = new JSONArray(result);
        Assertions.assertEquals(1, array.length());
        Assertions.assertEquals(3, array.getJSONObject(0).opt("id"));
    }

    @Test
    public void userPropertiesChangedCorrectly() throws Exception {
        registerNewDefaultUser("user", "123321").andExpect(status().isOk());
        var expectedUser = UserDto
                .builder()
                .birthDate(LocalDate.from(LocalDate.EPOCH))
                .firstName("Java")
                .secondName("Java")
                .id(0) // id is not shown to user
                .username("user")
                .build();
        mockMvc
                .perform(put("/users/2")
                        .content("{\"firstName\": \"Java\",\"secondName\": \"Java\",\"birthDate\": \"01-01-1970\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(appUserHttpBasic(appProperties.getDefaultAdminUsername(),
                                appProperties.getDefaultAdminPassword())))
                .andExpect(status().isOk());
        UserDto putUser = expectToObject(mockMvc
                .perform(get("/user").with(appUserHttpBasic("user", "123321")))
                .andExpect(status().isOk()), UserDto.class);
        Assertions.assertEquals(expectedUser, putUser);
    }

    @Test
    public void catsAreCreatedSuccessfully() throws Exception {
        registerNewDefaultUser("user", "123").andExpect(status().isOk());
        registerNewDefaultUser("user2", "123").andExpect(status().isOk());
        var cat1 = defaultCat("Ayana");
        cat1.setOwnerId(2);

        mockMvc.perform(post("/cats")).andExpect(status().isUnauthorized());
        registerCat(cat1,
                appProperties.getDefaultAdminUsername(),
                appProperties.getDefaultAdminPassword()).andExpect(status().isOk());
        registerCat(defaultCat("Kitty"), "user", "123").andExpect(status().isOk());
        getCatById(1, "user2", "123").andExpect(status().isForbidden());
        var firstCat = expectToObject(getCatById(1, "user", "123").andExpect(status().isOk()), CatDto.class);
        var secondCat = expectToObject(getCatById(2, "user", "123").andExpect(status().isOk()), CatDto.class);

        Assertions.assertEquals(2, firstCat.getOwnerId());
        Assertions.assertEquals(2, secondCat.getOwnerId());
        Assertions.assertEquals("Ayana", firstCat.getName());
        Assertions.assertEquals("Kitty", secondCat.getName());
    }


    @Test
    public void friendOpsAreCorrect() throws Exception {
        registerNewDefaultUser("user1", "123").andExpect(status().isOk());
        registerNewDefaultUser("user2", "1234").andExpect(status().isOk());
        for (int i = 0; i < 3; ++i) {
            var cat = defaultCat("kitty");
            registerCat(cat, "user1", "123").andExpect(status().isOk());
        }
        for (int i = 0; i < 3; ++i) {
            var cat = defaultCat("kitty");
            registerCat(cat, "user2", "1234").andExpect(status().isOk());
        }
        friendCats(1, 2, "user1", "123").andExpect(status().isOk());
        friendCats(1, 3, "user1", "123").andExpect(status().isOk());
        friendCats(1, 4, "user1", "123").andExpect(status().isOk());
        friendCats(1, 5, "user1", "123").andExpect(status().isOk());
        friendCats(4, 1, "user2", "1234").andExpect(status().isOk());
        friendCats(6, 1, "user2", "1234").andExpect(status().isOk());

        friendCats(1, 6, "user2", "1234").andExpect(status().isForbidden());

        JSONArray incomingInvites = new JSONArray(mockMvc
                .perform(get("/cats/1/friends?friendshipType=incoming").with(appUserHttpBasic("user1", "123")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        JSONArray outgoingInvites = new JSONArray(mockMvc
                .perform(get("/cats/1/friends?friendshipType=outgoing").with(appUserHttpBasic("user1", "123")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        JSONArray mutualFriends = new JSONArray(mockMvc
                .perform(get("/cats/1/friends").with(appUserHttpBasic("user1", "123")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());

        mockMvc
                .perform(post("/cats/1/friends")
                        .content("{\"friendId\" : 1}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(appUserHttpBasic("user1", "123")))
                .andExpect(status().isBadRequest());

        Assertions.assertEquals(3, mutualFriends.length());
        unfriendCats(1, 3, "user1", "123").andExpect(status().isOk());

        mutualFriends = new JSONArray(mockMvc
                .perform(get("/cats/1/friends").with(appUserHttpBasic("user1", "123")))
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


    private <T> T expectToObject(ResultActions expect, Class<T> clazz) throws UnsupportedEncodingException {
        return fromJsonString(expect.andReturn().getResponse().getContentAsString(), clazz);
    }

    public static RequestPostProcessor appUserHttpBasic(String username, String password) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(username, password);
    }

    private UserDto.UserDtoBuilder getDefaultUser() {
        return UserDto.builder().firstName("John").secondName("Doe");
    }

    private ResultActions registerNewDefaultUser(String username, String password) throws Exception {
        return mockMvc.perform(post("/users")
                .content(asJsonString(getDefaultUser().username(username).password(password).build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(appUserHttpBasic(appProperties.getDefaultAdminUsername(),
                        appProperties.getDefaultAdminPassword())));
    }

    private ResultActions registerCat(CatDto cat, String username, String password) throws Exception {
        return mockMvc.perform(post("/cats")
                .content(asJsonString(cat))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(appUserHttpBasic(username, password)));
    }

    private ResultActions getUserById(long id, String username, String password) throws Exception {
        return mockMvc.perform(get("/users/" + (id)).with(appUserHttpBasic(username, password)));
    }

    private ResultActions getCatById(long id, String username, String password) throws Exception {
        return mockMvc.perform(get("/cats/" + (id)).with(appUserHttpBasic(username, password)));
    }

    public static CatDto defaultCat(String name) {
        return CatDto.builder().name(name).colour(Colour.GREY).birthDate(LocalDate.now()).build();
    }

    private ResultActions friendCats(long catId, long friendId, String username, String password) throws Exception {
        return mockMvc.perform(post("/cats/" + catId + "/friends")
                .content(String.format("{\"friendId\" : %d}", friendId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(appUserHttpBasic(username, password)));
    }

    private ResultActions unfriendCats(long catId, long friendId, String username, String password) throws Exception {
        return mockMvc.perform(delete("/cats/" + catId + "/friends")
                .content(String.format("{\"friendId\" : %d}", friendId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(appUserHttpBasic(username, password)));
    }

    public String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
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

