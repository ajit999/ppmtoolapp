package com.yash.ppmtoolapi.service;

import java.util.List;

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
	

	
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		// make sure that backlog id exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if (backlog == null) {
			throw new ProjectNotFoundException("Project with id: " + backlog_id + " does not exists");
		}

		// make sure that project task id exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if (projectTask == null) {
			throw new ProjectNotFoundException("Project Task with id :'" + pt_id + "' does not exists");
		}

		// make sure that backlog_id and project identifier are same
		if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Backlog id " + backlog_id + "does not match with the project identifier"
					+ projectTask.getProjectIdentifier());
		}
		return projectTask;
	}
	
	public ProjectTask UpdateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
		//find the existing project task
		ProjectTask projectTask=findPTByProjectSequence(backlog_id, pt_id);
		// replace the project task with the updated task
		projectTask=updatedTask;
		
		//save the project
		return projectTaskRepository.save(projectTask);
	}
	
	public void deletePTByProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask= findPTByProjectSequence(backlog_id, pt_id);
		Backlog backlog= projectTask.getBacklog();
		List<ProjectTask> pts= backlog.getProjectTasks();
		pts.remove(projectTask);
		backlogRepository.save(backlog);
		projectTaskRepository.delete(projectTask);
	}
	
	
	
}