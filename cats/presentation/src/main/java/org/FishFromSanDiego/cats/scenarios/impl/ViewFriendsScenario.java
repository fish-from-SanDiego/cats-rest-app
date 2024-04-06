package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.CatService;

import java.util.List;
import java.util.Optional;

public class ViewFriendsScenario extends ScenarioBase {
    private final CatService catService;

    public ViewFriendsScenario(
            Scenario previousScenario, DaoContext daoContext, CatService catService) {
        super(previousScenario, "View all cat friends", "Friends (and invites)", daoContext);
        this.catService = catService;
    }

    @Override
    public Optional<Scenario> execute() {
        List<CatDto> friends;
        List<CatDto> friendsIncoming;
        List<CatDto> friendsOutgoing;
        try {
            friends = catService.getCatFriends();
            friendsIncoming = catService.getCatFriendIncomingInvites();
            friendsOutgoing = catService.getCatFriendOutgoingInvites();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.flush();
        System.out.println(this.getTitle());
        System.out.println("Friends");
        friends.forEach(cat -> System.out.println(cat.toString()));
        System.out.println("Incoming friend invites");
        friendsIncoming.forEach(cat -> System.out.println(cat.toString()));
        System.out.println("Outgoing friend invites");
        friendsOutgoing.forEach(cat -> System.out.println(cat.toString()));
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
