package com.bestellen.click_munch.menu;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlateRepository extends CrudRepository<Plate, Integer> {


    @Query("SELECT store_id FROM plates WHERE id = :plateId")
    Integer findStoreByPlate(@Param("plateId") Integer plateId);
}
