package com.grocerio.entities.listItem;

import com.grocerio.entities.item.model.Item;
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
public interface ListItemRepository extends JpaRepository<ListItem, Long> {
    @Query("SELECT li FROM ListItem li WHERE li.item.shelf.id = :shelfId")
    List<ListItem> findAllByShelfId(@Param("shelfId") Long shelfId);

    @Query("SELECT li FROM ListItem li WHERE li.id = :id AND li.item.shelf.id = :shelfId")
    Optional<ListItem> findByIdAndShelfId(@Param("id") Long id, @Param("shelfId") Long shelfId);

    @Query("SELECT li FROM ListItem li WHERE li.item.name = :name AND li.item.shelf.id = :shelfId")
    Optional<ListItem> findByNameAndShelfId(@Param("name") String name, @Param("shelfId") Long shelfId);

    @Modifying
    @Query("DELETE FROM ListItem li WHERE li.id = :id AND li.item.shelf.id = :shelfId")
    void deleteByIdAndShelfId(@Param("id") Long id, @Param("shelfId") Long shelfId);
}

