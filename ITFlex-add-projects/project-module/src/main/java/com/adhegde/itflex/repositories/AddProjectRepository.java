package com.adhegde.itflex.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.adhegde.itflex.model.Projects;

public interface AddProjectRepository extends MongoRepository<Projects, Long> {

    Projects findByProjectId(Long id);

    Projects findByProjectName(String projectName);
}
