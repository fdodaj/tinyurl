package com.lufthansa.TinyUrl.repository;

import com.lufthansa.TinyUrl.entity.ClickActivity;
import com.lufthansa.TinyUrl.entity.ClickActivityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClickActivityRepository extends JpaRepository<ClickActivity, ClickActivityId> {

    Optional<ClickActivity> findById(ClickActivityId id);


}
