package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.exceptions.CantFriendSelfException;
import org.FishFromSanDiego.cats.exceptions.CatAlreadyFriendedException;
import org.FishFromSanDiego.cats.exceptions.CatNotFoundException;
import org.FishFromSanDiego.cats.exceptions.CatsAreNotFriendsException;
import org.FishFromSanDiego.cats.models.Cat;
import org.FishFromSanDiego.cats.repositories.CatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class CatsServiceImpl implements CatsService {
    private final CatsRepository catsRepository;

    @Autowired
    public CatsServiceImpl(CatsRepository catsRepository) {
        this.catsRepository = catsRepository;
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
        Cat friend = catsRepository.findById(requestingCatId).orElseThrow(CatNotFoundException::new);
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
        Cat friend = catsRepository.findById(requestingCatId).orElseThrow(CatNotFoundException::new);
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
        return catsRepository.findById(id)
                .orElseThrow(CatNotFoundException::new)
                .getFriends()
                .stream()
                .map(Cat::getDto)
                .toList();
    }

    @Override
    public List<CatDto> getCatFriendIncomingInvitesById(long id) {
        Cat cat = catsRepository.findById(id).orElseThrow(CatNotFoundException::new);
        List<Cat> friends = cat.getFriends();
        var difference = new HashSet<>(catsRepository.findAllCatsForWhomThisIsFriend(cat));
        difference.removeAll(new HashSet<>(friends));
        return difference.stream().map(Cat::getDto).toList();
    }

    @Override
    public List<CatDto> getCatFriendOutgoingInvitesById(long id) {
        Cat cat = catsRepository.findById(id).orElseThrow(CatNotFoundException::new);
        List<Cat> friends = cat.getFriends();
        var difference = new HashSet<>(friends);
        difference.removeAll(new HashSet<>(catsRepository.findAllCatsForWhomThisIsFriend(cat)));
        return difference.stream().map(Cat::getDto).toList();
    }

    @Override
    public List<CatDto> getCatsByOwnerId(long ownerId) {
        return catsRepository.findAllByOwnerId(ownerId).stream().map(Cat::getDto).toList();
    }

    @Override
    public List<CatDto> getAllCats() {
        return catsRepository.findAll().stream().map(Cat::getDto).toList();
    }

    @Transactional(readOnly = false)
    @Override
    public CatDto registerNewCat(CatDto cat) {
        return catsRepository.save(Cat.fromDto(cat)).getDto();
    }

    @Transactional(readOnly = false)
    @Override
    public void removeCatById(long id) {
        if (!catsRepository.existsById(id))
            throw new CatNotFoundException();
        catsRepository.deleteById(id);
    }
}
