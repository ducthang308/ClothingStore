package com.example.demo.Services;

import com.example.demo.DTO.DiscountsDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Discounts;
import com.example.demo.Models.Users;
import com.example.demo.Repository.DiscountsRepository;
import com.example.demo.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountsService implements IDiscountsService {

    private final DiscountsRepository discountsRepository;
    private final UsersRepository usersRepository;


    @Override
    public Discounts createDiscount(DiscountsDTO discountsDTO) throws DataNotFoundException {
        Users existingUser = usersRepository.findById(discountsDTO.getUserId())
                .orElseThrow(()->
                        new DataNotFoundException("Not found user id: "+discountsDTO.getUserId()));
        Discounts discounts = Discounts.builder()
                .percent(discountsDTO.getPercent())
                .users(existingUser)
                .build();
        return discountsRepository.save(discounts);
    }

    @Override
    public Discounts updateDiscount(long id, DiscountsDTO discountsDTO) throws DataNotFoundException {
        Discounts existingDiscount = discountsRepository.findById(id)
                .orElseThrow(()->
                        new DataNotFoundException("Not found discount id: "+id));
        existingDiscount.setPercent(discountsDTO.getPercent());
        return discountsRepository.save(existingDiscount);
    }

    @Override
    public void deleteDiscount(long id) {
        discountsRepository.deleteById(id);
    }

    @Override
    public List<Discounts> getAllDiscountsByUsersId(Long userId) {
        return discountsRepository.findByUsersId(userId);
    }
}
