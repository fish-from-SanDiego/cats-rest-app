import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.FishFromSanDiego.cats.dao.CatDaoImpl;
import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dao.UserDaoImpl;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.exceptions.*;
import org.FishFromSanDiego.cats.models.Colour;
import org.FishFromSanDiego.cats.services.CatServiceImpl;
import org.FishFromSanDiego.cats.services.UserManagementService;
import org.FishFromSanDiego.cats.services.UserManagementServiceImpl;
import org.FishFromSanDiego.cats.services.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@PersistenceContext(unitName = "cats-test-db")
public class DaoServiceTests {
    private EntityManagerFactory emf;
    private DaoContext dc;

    @BeforeEach
    public void init() {
        emf = Persistence.createEntityManagerFactory("cats-test-db");
        dc = new DaoContext(new CatDaoImpl(emf), new UserDaoImpl(emf));
    }

    @AfterEach
    public void cleanup() {
        emf.close();
    }

    @Test
    public void usersAreRegisteredCorrectly() throws DatabaseSideException {
        UserManagementService ums = new UserManagementServiceImpl(dc);
        for (int i = 0; i < 10; ++i)
            ums.registerNewUser(UserDto.builder().firstName(String.format("John %d", i)).build());

        var users = ums.getAllUserInfos();
        Assertions.assertEquals(10, users.size());
        for (int i = 0; i < 10; ++i) {
            Assertions.assertEquals(i + 1, users.get(i).getId());
            Assertions.assertEquals(String.format("John %d", i), users.get(i).getFirstName());
            Assertions.assertNull(users.get(i).getSecondName());
            Assertions.assertNull(users.get(i).getBirthDate());
        }
    }

    @Test
    public void usersAreRemovedCorrectly() throws DatabaseSideException, NoUserWithSuchIdException {
        UserManagementService ums = new UserManagementServiceImpl(dc);
        ums.registerNewUser(UserDto.builder().firstName("John").secondName("Doe").build());
        ums.registerNewUser(UserDto.builder().firstName("John").secondName("Doe").build());

        var users = ums.getAllUserInfos();

        Assertions.assertThrowsExactly(NoUserWithSuchIdException.class, () -> {
            ums.removeUser(112);
        });
        ums.removeUser(1);

        Assertions.assertEquals(1, ums.getAllUserInfos().size());
        Assertions.assertEquals(2, ums.getAllUserInfos().get(0).getId());
    }

    @Test
    public void userPropertiesChangedCorrectly() throws DatabaseSideException, NoUserWithSuchIdException {
        UserManagementService ums = new UserManagementServiceImpl(dc);
        ums.registerNewUser(UserDto.builder().firstName("John").secondName("Doe").build());
        var us = new UserServiceImpl(dc, 1);

        us.setUserBirthDate(LocalDate.from(LocalDate.EPOCH));
        Assertions.assertEquals(LocalDate.EPOCH, us.getUserInfo().getBirthDate());
        us.setUserSecondName("Java");
        Assertions.assertEquals("Java", us.getUserInfo().getSecondName());
        us.setUserFirstName("Java");
        Assertions.assertEquals("Java", us.getUserInfo().getFirstName());
    }

    @Test
    public void catPropertiesChangedCorrectly() throws
            DatabaseSideException,
            NoUserWithSuchIdException,
            CatBelongsToOtherUserException,
            NoCatWithSuchIdException {
        UserManagementService ums = new UserManagementServiceImpl(dc);
        ums.registerNewUser(UserDto.builder().firstName("John").secondName("Doe").build());
        var us = new UserServiceImpl(dc, 1);
        us.registerNewCat(defaultCat("Ayana"));
        var cs = new CatServiceImpl(dc, 1, 1);

        cs.setCatName("Morgen");
        Assertions.assertEquals("Morgen", cs.getCatInfo().getName());

        cs.setCatBreed("dpmk,sf");
        Assertions.assertEquals("dpmk,sf", cs.getCatInfo().getBreed());

        cs.setCatBirthDate(LocalDate.EPOCH);
        Assertions.assertEquals(LocalDate.EPOCH, cs.getCatInfo().getBirthDate());

        cs.setCatColour(Colour.CHOCOLATE);
        Assertions.assertEquals(Colour.CHOCOLATE, cs.getCatInfo().getColour());
    }

    @Test
    public void friendOpsAreCorrect() throws
            DatabaseSideException,
            NoUserWithSuchIdException,
            NoCatFriendWithSuchIdException,
            OtherCatIsAlreadyThisCatFriendException,
            CatBelongsToOtherUserException,
            NoCatWithSuchIdException,
            OtherCatIsNotThisCatFriendException,
            CantFriendSelfException {
        UserManagementService ums = new UserManagementServiceImpl(dc);
        ums.registerNewUser(UserDto.builder().firstName("John").secondName("Doe").build());
        ums.registerNewUser(UserDto.builder().firstName("Doe").secondName("John").build());
        var us1 = new UserServiceImpl(dc, 1);
        var us2 = new UserServiceImpl(dc, 2);
        for (int i = 0; i < 3; ++i)
            us1.registerNewCat(defaultCat("kitty"));
        for (int i = 0; i < 3; ++i)
            us2.registerNewCat(defaultCat("kitty"));
        var cs = new CatServiceImpl(dc, 1, 1);
        cs.friendCat(2);
        cs.friendCat(3);
        cs.friendCat(4);
        cs.friendCat(5);
        new CatServiceImpl(dc, 4, 2).friendCat(1);
        new CatServiceImpl(dc, 6, 2).friendCat(1);
        var friendsIncoming = cs.getCatFriendIncomingInvites();
        var friendsOutgoing = cs.getCatFriendOutgoingInvites();

        Assertions.assertThrowsExactly(CantFriendSelfException.class, () -> {
            cs.friendCat(1);
        });
        Assertions.assertEquals(3, cs.getCatFriends().size());
        cs.unfriendCat(3);
        var friends = cs.getCatFriends();

        Assertions.assertEquals(2, friends.size());
        Assertions.assertEquals(1, friendsIncoming.size());
        Assertions.assertEquals(1, friendsOutgoing.size());
        Assertions.assertEquals(5, friendsOutgoing.get(0).getId());
        Assertions.assertEquals(6, friendsIncoming.get(0).getId());
    }

    @Test
    public void catsAreCreatedAndRemovedCorrectly() throws
            DatabaseSideException,
            NoUserWithSuchIdException,
            CatBelongsToOtherUserException,
            NoCatWithSuchIdException {
        UserManagementService ums = new UserManagementServiceImpl(dc);
        ums.registerNewUser(UserDto.builder().firstName("John").secondName("Doe").build());
        ums.registerNewUser(UserDto.builder().firstName("Don").secondName("Simon").build());

        var us1 = new UserServiceImpl(dc, 1);
        var us2 = new UserServiceImpl(dc, 2);
        us1.registerNewCat(defaultCat("Maxwell"));
        us1.registerNewCat(defaultCat("Gurenyaaa"));

        us2.registerNewCat(defaultCat("Jotaro"));

        Assertions.assertEquals(2, us1.getAllCatInfos().size());
        Assertions.assertEquals(1, us2.getAllCatInfos().size());
        Assertions.assertThrowsExactly(CatBelongsToOtherUserException.class, () -> {
            us1.getCatById(3);
        });
        Assertions.assertThrowsExactly(NoCatWithSuchIdException.class, () -> {
            us1.getCatById(453);
        });

        us2.removeCat(3);
        Assertions.assertEquals(2, dc.getCatDao().getAllCats().size());
        Assertions.assertEquals(2, us1.getAllCatInfos().size());
    }

    @Test
    public void removedUserscatsAreRemovedCorrectly() throws DatabaseSideException, NoUserWithSuchIdException {
        UserManagementService ums = new UserManagementServiceImpl(dc);
        ums.registerNewUser(UserDto.builder().firstName("John").secondName("Doe").build());
        ums.registerNewUser(UserDto.builder().firstName("John").secondName("Doe").build());

        var us1 = new UserServiceImpl(dc, 1);
        us1.registerNewCat(defaultCat("Maxwell"));
        us1.registerNewCat(defaultCat("Gurenyaaa"));

        new UserServiceImpl(dc, 2).registerNewCat(defaultCat("a"));
        ums.removeUser(1);

        Assertions.assertEquals(1, dc.getCatDao().getAllCats().size());
    }

    // просто чтобы чекнуть, что h2 парвильно пересоздаёт схему ¯\_(ツ)_/¯
    @Test
    public void h2RecreatesSchemaAfterEachTest1() throws DatabaseSideException {
        var user = UserDto.builder().firstName("John").build();
        dc.getUserDao().addNewUser(user);
        Assertions.assertEquals(1, dc.getUserDao().getAllUsers().size());
    }

    @Test
    public void h2RecreatesSchemaAfterEachTest2() throws DatabaseSideException {
        var user = UserDto.builder().firstName("John").build();
        dc.getUserDao().addNewUser(user);

        Assertions.assertEquals(1, dc.getUserDao().getAllUsers().size());
    }

    public static CatDto defaultCat(String name) {
        return CatDto.builder().name(name).colour(Colour.GREY).birthDate(LocalDate.now()).build();
    }

}
