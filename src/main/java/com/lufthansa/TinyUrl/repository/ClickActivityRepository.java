package com.lufthansa.TinyUrl.repository;

import com.lufthansa.TinyUrl.entity.ClickActivity;
import com.lufthansa.TinyUrl.entity.ClickActivityId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClickActivityRepository extends JpaRepository<ClickActivity, ClickActivityId> {

    Optional<ClickActivity> findById(ClickActivityId id);

    // Custom query to insert or update the click count
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO click_activity (user_id, url_id, click_count) " +
            "VALUES (:userId, :urlId, 1) " +
            "ON CONFLICT (user_id, url_id) " +
            "DO UPDATE SET click_count = click_activity.click_count + 1",
            nativeQuery = true)
    void incrementClickCount(@Param("userId") Long userId, @Param("urlId") Long urlId);

}
