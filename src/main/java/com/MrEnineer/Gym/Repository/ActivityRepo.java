package com.MrEnineer.Gym.Repository;

import com.MrEnineer.Gym.Entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepo extends JpaRepository<Activity,String> {
    List<Activity> findByUserId(String userId);
}
