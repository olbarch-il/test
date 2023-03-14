package com.task.dropit.dao;

import com.task.dropit.model.postcodes.PostCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCodeRepository extends JpaRepository<PostCodeEntity, String> {

}
