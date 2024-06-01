package org.FishFromSanDiego.cats.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.dto.CatFriendIdRequest;
import org.FishFromSanDiego.cats.dto.CatView;
import org.FishFromSanDiego.cats.models.Colour;
import org.FishFromSanDiego.cats.models.FriendshipType;
import org.FishFromSanDiego.cats.services.CatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@RestController
@EnableAutoConfiguration
@RequestMapping("/cats")
@ComponentScan({"org.FishFromSanDiego.cats.services", "org.FishFromSanDiego.cats.controllers"})
public class CatsController {
    private final CatsService catsService;

    @Autowired
    public CatsController(CatsService catsService) {
        this.catsService = catsService;
    }

    @GetMapping
    public List<CatDto> showAllCats(
            @RequestParam(name = "ownerId", required = false) Optional<Long> ownerId,
            @RequestParam(name = "colour", required = false) Optional<Colour> colour) {
        var catExample = new CatDto();
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnorePaths("id");
        if (ownerId.isPresent()) {
            catExample.setOwnerId(ownerId.get());
            matcher.withMatcher("owner", exact());
        } else {
            matcher = matcher.withIgnorePaths("owner");
        }
        if (colour.isPresent()) {
            catExample.setColour(colour.get());
            matcher.withMatcher("colour", exact());
        }

        return catsService.getCatsByMatcher(matcher, catExample);
    }

    @GetMapping(path = "/{id}")
    public CatDto showCat(@PathVariable("id") long id) {
        return catsService.getCatById(id);
    }

    @PostMapping
    public CatDto registerNewCat(
            @JsonView(CatView.PostRequest.class) @RequestBody @Valid CatDto cat) {
        return catsService.registerNewCat(cat);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> updateCat(
            @JsonView(CatView.PutRequest.class) @RequestBody @Valid CatDto cat, @PathVariable("id") long id) {
        cat.setId(id);
        catsService.updateCat(cat);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> removeCat(@PathVariable("id") long id) {
        catsService.removeCatById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    public List<CatDto> showCatFriends(
            @RequestParam(value = "friendshipType", required = false, defaultValue = "MUTUAL") FriendshipType friendshipType,
            @PathVariable("id") long id) {
        return switch (friendshipType) {
            case MUTUAL -> catsService.getCatFriendsById(id);
            case INCOMING -> catsService.getCatFriendIncomingInvitesById(id);
            case OUTGOING -> catsService.getCatFriendOutgoingInvitesById(id);
        };
    }

    @PostMapping("/{id}/friends")
    public ResponseEntity<HttpStatus> friendCat(
            @RequestBody CatFriendIdRequest requestBody, @PathVariable("id") long id) {
        catsService.friendCat(id, requestBody.getFriendId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/friends")
    public ResponseEntity<HttpStatus> unfriendCat(
            @RequestBody CatFriendIdRequest requestBody, @PathVariable("id") long id) {
        catsService.unfriendCat(id, requestBody.getFriendId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
