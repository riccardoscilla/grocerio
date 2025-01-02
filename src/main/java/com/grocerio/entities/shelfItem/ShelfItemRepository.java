package com.grocerio.entities.shelfItem;

import com.grocerio.entities.listItem.model.ListItem;
import com.grocerio.entities.shelfItem.model.ShelfItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelfItemRepository extends JpaRepository<ShelfItem, Long> {
    @Query("SELECT si FROM ShelfItem si WHERE si.item.shelf.id = :shelfId")
    List<ShelfItem> findAllByShelfId(@Param("shelfId") Long shelfId);

    @Query("SELECT si FROM ShelfItem si WHERE si.id = :id AND si.item.shelf.id = :shelfId")
    Optional<ShelfItem> findByIdAndShelfId(@Param("id") Long id, @Param("shelfId") Long shelfId);

    @Query("SELECT si FROM ShelfItem si WHERE si.item.name = :name AND si.item.shelf.id = :shelfId")
    Optional<ShelfItem> findByNameAndShelfId(@Param("name") String name, @Param("shelfId") Long shelfId);

    @Modifying
    @Query("DELETE FROM ShelfItem si WHERE si.id = :id AND si.item.shelf.id = :shelfId")
    void deleteByIdAndShelfId(@Param("id") Long id, @Param("shelfId") Long shelfId);
}
