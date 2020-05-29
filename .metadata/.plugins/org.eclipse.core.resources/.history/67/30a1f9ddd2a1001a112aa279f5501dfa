/**
 * 
 */
package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Backlog;
import com.example.demo.domain.Project;
import com.example.demo.exception.ProjectIdException;
import com.example.demo.repository.BacklogRepository;
import com.example.demo.repository.ProjectRepository;

/**
 * @author Govind
 *
 */
//@Service
//public class ProjectService {
//	@Autowired
//	private ProjectRepository projectRepository;
//	
//	public Project saveOrUpdate(Project project) {
//		
//		
//		try {
//			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
//			
//			//when project is getting created first time, then backlog should be created along with that .
//			if(project.getId()==null)
//			{
//				Backlog backlog=new Backlog();
//				project.setBacklog(backlog); //one to one relationship
//				backlog.setProject(project); //one to one relationship
//				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
//				
//			}
//			//in case of updating of project backlog should not be null,same projectIdentifier should be set in a backlog
//			if(project.getId()!=null)
//			{
//				project.setBacklog(BacklogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
//			}
//			return projectRepository.save(project);
//		} catch(Exception ex) {
//			throw new ProjectIdException("Project Id '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
//		}
//	}
//	
//	public Project findProjectByIdentifier(String projectId) {
//		Project project =  projectRepository.findByProjectIdentifier(projectId.toUpperCase());
//		if(project==null) { 
//			throw new ProjectIdException("Project Id '"+projectId.toUpperCase()+"' does not exists");
//		}
//		return project;
//	}
//	
//	public Iterable<Project> findAllProjcts(){
//		return projectRepository.findAll();
//	}
//
//	public void deleteProjectByIdentifier(String projectId) {
//        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
//
//        if(project == null){
//            throw  new  ProjectIdException("Cannot delete Project with ID '"+projectId+"'. This project does not exist");
//        }
//
//        projectRepository.delete(project);		
//	}
//
//}


@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			if(project.getId()==null) {
				Backlog backlog=new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			if(project.getId()!=null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			return projectRepository.save(project);
		}catch(Exception ex) {
			throw new ProjectIdException("Project Id '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
		}
		
	}
	
	public Project findProjectByIdentifier(String projectId) {
		Project project=projectRepository.findByProjectIdentifier(projectId);
		if(project==null) {
			throw new ProjectIdException("Project Id '"+projectId+"' does not exist");
		}
		return project;
	}
	
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectId) {
		Project project=projectRepository.findByProjectIdentifier(projectId);
		if(project==null) {
			throw new ProjectIdException("Cannot delete project wid id '"+projectId+"' this project not exist");
		}
		projectRepository.delete(project);
		
	}
}

