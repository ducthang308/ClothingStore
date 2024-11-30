package com.example.demo.Repository;

import com.example.demo.Models.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartsRepository extends JpaRepository<Carts, Long> {

    @Query("SELECT c.id FROM Carts c " +
            "WHERE c.users.id = :userId")
    Long findCartIdByUserId(@Param("userId") Long userId);
}
