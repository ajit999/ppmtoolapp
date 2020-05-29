package com.yash.ppmtoolapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.ppmtoolapi.domain.Backlog;
import com.yash.ppmtoolapi.domain.Project;
import com.yash.ppmtoolapi.domain.ProjectTask;
import com.yash.ppmtoolapi.exception.ProjectNotFoundException;
import com.yash.ppmtoolapi.repository.BacklogRepository;
import com.yash.ppmtoolapi.repository.ProjectRepository;
import com.yash.ppmtoolapi.repository.ProjectTaskRepository;

//@Service
//public class ProjectTaskService {
//
//	@Autowired
//	private BacklogRepository backlogRepository;
//	
//	@Autowired
//	private ProjectTaskRepository projectTaskRepository;
//	
//	public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask) {
//		
//		//Exceptions: Project Not Found
//		
//		//ProjectTasks to be added to specific project,project!=null,Backlog exists
//		
//		Backlog backlog=BacklogRepository.findByProjectIdentifier(projectIdentifier);
//		
//		//Set the backlog to project task
//		projectTask.setBacklog(backlog);
//		
//		//We want our project sequence to be like: IDPRO-1,IDPRO-2
//		
//		Integer backLogSequence=backlog.getPTSequence();
//		
//		System.out.println("Backlog Sequence = "+backLogSequence);
//		backLogSequence++;
//		
//		backlog.setPTSequence(backLogSequence);
//		
//		//Add backlogSequence to project task
//		projectTask.setProjectSequence(projectIdentifier+"-"+backLogSequence);
//		
//		projectTask.setProjectIdentifier(projectIdentifier);
//		
//		//Initial priority when priority is null
//		if(projectTask.getPriority()==null) {
//			projectTask.setPriority(3);
//		}
//		
//		//Initial status when status is null
//		
//		if(projectTask.getStatus()==""|| projectTask.getStatus()==null) {
//			projectTask.setStatus("TO_DO");
//		}
//		
//		return projectTaskRepository.save(projectTask);
//		
//		
//	}
//}

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask) {
		
		try {
			//Exceptions: Project Not Found
			
			//ProjectTasks to be added to specific project,project!=null,Backlog exists
			
			Backlog backlog=backlogRepository.findByProjectIdentifier(projectIdentifier);
			
			//Set the backlog to project task
			projectTask.setBacklog(backlog);
			
			//We want our project sequence to be like: IDPRO-1,IDPRO-2
			
			Integer backLogSequence=backlog.getPTSequence();
			
			System.out.println("Backlog Sequence = "+backLogSequence);
			backLogSequence++;
			
			backlog.setPTSequence(backLogSequence);
			
			//Add backlogSequence to project task
			projectTask.setProjectSequence(projectIdentifier+"-"+backLogSequence);
			
			projectTask.setProjectIdentifier(projectIdentifier);
			
			//Initial priority when priority is null
			if(projectTask.getPriority()==null) {
				projectTask.setPriority(3);
			}
			
			//Initial status when status is null
			
			if(projectTask.getStatus()==""|| projectTask.getStatus()==null) {
				projectTask.setStatus("TO_DO");
			}
			
			return projectTaskRepository.save(projectTask);

			
		} catch (Exception e) {
			throw new ProjectNotFoundException("project not found");
		}		
		
	}
	public Iterable<ProjectTask> findBacklogById(String id)
	{
		Project project= projectRepository.findByProjectIdentifier(id);
		if(project==null)
		{
			throw new ProjectNotFoundException("project with id "+id+ " not found");
		}
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id)
	{
		return projectTaskRepository.findByProjectSequence(pt_id);
	}
}