package com.grocerio.entities.item;

import com.grocerio.entities.item.model.Item;
import com.grocerio.entities.shelfItem.model.ShelfItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.shelf.id = :shelfId")
    List<Item> findAllByShelfId(@Param("shelfId") Long shelfId);

    @Query("SELECT i FROM Item i WHERE i.id = :id AND i.shelf.id = :shelfId")
    Optional<Item> findByIdAndShelfId(@Param("id") Long id, @Param("shelfId") Long shelfId);

    @Query("SELECT i FROM Item i WHERE i.name = :name AND i.shelf.id = :shelfId")
    Optional<Item> findByNameAndShelfId(@Param("name") String name, @Param("shelfId") Long shelfId);
}
