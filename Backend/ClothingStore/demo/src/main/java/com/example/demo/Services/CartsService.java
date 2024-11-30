package com.example.demo.Services;

import com.example.demo.DTO.CartsDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Carts;
import com.example.demo.Models.Users;
import com.example.demo.Repository.CartsRepository;
import com.example.demo.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartsService implements ICartsService {
    private final UsersRepository usersRepository;
    private final CartsRepository cartsRepository;

    @Override
    public Carts createCart(CartsDTO cartsDTO) throws DataNotFoundException {
        Users existingUser = usersRepository.findById(cartsDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException("Not found userId: "+cartsDTO.getUserId()));
        Carts carts = Carts.builder()
                .users(existingUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return cartsRepository.save(carts);
    }

    @Override
    public Long getCartByUserId(long userId) {
        return cartsRepository.findCartIdByUserId(userId);
    }

    @Override
    public List<Carts> getAllCarts() {
        return cartsRepository.findAll();
    }

    @Override
    public Carts updateCart(long id) throws DataNotFoundException {
        Carts carts = cartsRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not found id: " + id));
        carts.setUpdatedAt(LocalDateTime.now());
        return cartsRepository.save(carts);
    }

    @Override
    public void deleteCart(long id) {
        cartsRepository.deleteById(id);
    }
}
