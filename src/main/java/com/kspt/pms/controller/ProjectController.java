package com.kspt.pms.controller;

import com.kspt.pms.entity.Project;
import com.kspt.pms.exception.NotFoundException;
import com.kspt.pms.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kivi on 17.12.17.
 */
@RestController
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    @RequestMapping("rest/project/{name}")
    public Project getProject(@PathVariable String name) {
        return projectRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(name));
    }
}
