package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.exceptions.*;
import org.FishFromSanDiego.cats.models.Cat;
import org.FishFromSanDiego.cats.models.User;
import org.FishFromSanDiego.cats.repositories.CatsRepository;
import org.FishFromSanDiego.cats.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class CatsServiceImpl implements CatsService {
    private final CatsRepository catsRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public CatsServiceImpl(CatsRepository catsRepository, UsersRepository usersRepository) {
        this.catsRepository = catsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public CatDto getCatById(long id) {
        return catsRepository.findById(id).orElseThrow(CatNotFoundException::new).getDto();
    }

    @Transactional(readOnly = false)
    @Override
    public void updateCat(CatDto updatedCat) {
        Cat cat = catsRepository.findById(updatedCat.getId()).orElseThrow(CatNotFoundException::new);
        catsRepository.save(cat.copyFromDto(updatedCat));
    }

    @Transactional(readOnly = false)
    @Override
    public void friendCat(long requestingCatId, long friendCatId) {
        if (requestingCatId == friendCatId)
            throw new CantFriendSelfException();
        Cat cat = catsRepository.findById(requestingCatId).orElseThrow(CatNotFoundException::new);
        Cat friend = catsRepository.findById(friendCatId).orElseThrow(CatNotFoundException::new);
        List<Cat> catfriends = cat.getFriends();
        if (catfriends.contains(friend))
            throw new CatAlreadyFriendedException();
        catfriends.add(friend);
        if (cat.getOwner().getId() == friend.getOwner().getId())
            friend.getFriends().add(cat);
        Cat[] cats = {cat, friend};
        catsRepository.saveAll(Arrays.asList(cats));
    }

    @Transactional(readOnly = false)
    @Override
    public void unfriendCat(long requestingCatId, long friendCatId) {
        if (requestingCatId == friendCatId)
            throw new CantFriendSelfException();
        Cat cat = catsRepository.findById(requestingCatId).orElseThrow(CatNotFoundException::new);
        Cat friend = catsRepository.findById(friendCatId).orElseThrow(CatNotFoundException::new);
        List<Cat> catfriends = cat.getFriends();
        if (!catfriends.contains(friend))
            throw new CatsAreNotFriendsException();
        catfriends.remove(friend);
        if (cat.getOwner().getId() == friend.getOwner().getId())
            friend.getFriends().remove(cat);
        Cat[] cats = {cat, friend};
        catsRepository.saveAll(Arrays.asList(cats));
    }

    @Override
    public List<CatDto> getCatFriendsById(long id) {
        Cat cat = catsRepository.findById(id).orElseThrow(CatNotFoundException::new);
        List<Cat> friends = cat.getFriends();
        var intersection = new HashSet<>(catsRepository.findAllCatsForWhomThisIsFriend(id));
        intersection.retainAll(new HashSet<>(friends));
        return intersection.stream().map(Cat::getDto).toList();
    }

    @Override
    public List<CatDto> getCatFriendIncomingInvitesById(long id) {
        Cat cat = catsRepository.findById(id).orElseThrow(CatNotFoundException::new);
        List<Cat> friends = cat.getFriends();
        var difference = new HashSet<>(catsRepository.findAllCatsForWhomThisIsFriend(cat.getId()));
        difference.removeAll(new HashSet<>(friends));
        return difference.stream().map(Cat::getDto).toList();
    }

    @Override
    public List<CatDto> getCatFriendOutgoingInvitesById(long id) {
        Cat cat = catsRepository.findById(id).orElseThrow(CatNotFoundException::new);
        List<Cat> friends = cat.getFriends();
        var difference = new HashSet<>(friends);
        difference.removeAll(new HashSet<>(catsRepository.findAllCatsForWhomThisIsFriend(cat.getId())));
        return difference.stream().map(Cat::getDto).toList();
    }

    @Override
    public List<CatDto> getCatsByOwnerId(long ownerId) {
        return catsRepository.findAllByOwnerId(ownerId).stream().map(Cat::getDto).toList();
    }

    @Override
    public List<CatDto> getCatsByMatcher(ExampleMatcher matcher, CatDto cat) {
        Cat temp = Cat.fromDto(cat);
        temp.setOwner(User.builder().id(cat.getOwnerId()).build());
        var example = Example.of(temp, matcher);
        return catsRepository.findAll(example).stream().map(Cat::getDto).toList();
    }


    @Override
    public List<CatDto> getAllCats() {
        return catsRepository.findAll().stream().map(Cat::getDto).toList();
    }

    @Transactional(readOnly = false)
    @Override
    public CatDto registerNewCat(CatDto cat) {
        Cat registeredCat = Cat.fromDto(cat);
        if (!usersRepository.existsById(cat.getOwnerId()))
            throw new UserNotFoundException();
        registeredCat.setOwner(usersRepository.getReferenceById(cat.getOwnerId()));
        return catsRepository.save(registeredCat).getDto();
    }

    @Transactional(readOnly = false)
    @Override
    public void removeCatById(long id) {
        if (!catsRepository.existsById(id))
            throw new CatNotFoundException();
        catsRepository.deleteById(id);
    }
}
