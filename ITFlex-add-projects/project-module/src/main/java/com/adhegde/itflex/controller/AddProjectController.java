package com.adhegde.itflex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.adhegde.itflex.model.Projects;
import com.adhegde.itflex.repositories.AddProjectRepository;


@RestController
public class AddProjectController {
    @Autowired
    AddProjectRepository addProjectRepository;

    @PostMapping(value = "/addProjects")
    public ResponseEntity addProject(@RequestBody Projects project){

        if(project.getProjectName()== "" ||
            project.getCompanyName()== ""||
            project.getNumberOfHours()== "" ||
            project.getNumberOfPpl()=="" ||
            project.getPayPerHour() == "" ||
            project.getProjectDepartment() == "" ||
            project.getProjectDescription() == ""||
            project.getProjectLink()=="" ||
            project.getProjectLocation()=="") {
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        }

        try{
            addProjectRepository.save(new Projects( project.getProjectName(),
                    project.getCompanyName(),
                    project.getNumberOfPpl(),
                    project.getNumberOfHours(),
                    project.getPayPerHour(),
                    project.getProjectDescription(),
                    project.getProjectLocation(),
                    project.getProjectDepartment(),
                    project.getProjectLink()
            ));
        }
        catch (DuplicateKeyException e1){ //|| MongoWriteException e){
           // System.out.println(HttpStatus.CONFLICT);
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
