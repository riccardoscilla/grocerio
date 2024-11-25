package com.grocerio.entities.category;

import com.grocerio.entities.category.model.Category;
import com.grocerio.entities.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.shelf.id = :shelfId")
    List<Category> findAllByShelfId(@Param("shelfId") Long shelfId);

    @Query("SELECT c FROM Category c WHERE c.id = :id AND c.shelf.id = :shelfId")
    Optional<Category> findByIdAndShelfId(@Param("id") Long id, @Param("shelfId") Long shelfId);

    @Modifying
    @Query("DELETE FROM Category i WHERE i.id = :id AND i.shelf.id = :shelfId")
    void deleteByIdAndShelfId(@Param("id") Long id, @Param("shelfId") Long shelfId);
}
