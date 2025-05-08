package com.apica_2.assignment.repository;


import com.apica_2.assignment.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Events, String> {
    List<Events> findAllByUserId(String id);
    List<Events> findAllByUserIdAndEventName(String id,String eventName);

}
