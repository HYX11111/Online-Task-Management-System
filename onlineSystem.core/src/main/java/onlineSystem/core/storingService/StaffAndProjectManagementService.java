package onlineSystem.core.storingService;

import java.util.List;
import javax.persistence.*;
import org.springframework.stereotype.Service;

import onlineSystem.core.enums.Department;
import onlineSystem.core.projectAndTasks.Project;
import onlineSystem.core.projectAndTasks.ProjectTask;
import onlineSystem.core.staff.Staff;

@Service
public class StaffAndProjectManagementService implements StaffAndProjectStoringService{
    
    @Override
    public String saveStaff(Staff staff) {
        EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();	
			
			em.getTransaction().begin();
			em.persist(staff);
			em.getTransaction().commit();			
			return staff.toString();
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
	}
 
    @Override
    public Staff getStaffById(Integer staffID) {
       EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			
			em.getTransaction().begin();
			Query query = em.createNativeQuery("SELECT * FROM staff WHERE s_id = :staffID", Staff.class); 
            query.setParameter("staffID", staffID);
            Staff staff = (Staff) query.getSingleResult();
			em.getTransaction().commit();		
			return staff;
		} catch(NoResultException e) {
			return null;
		}finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
    }

    @Override
    public String saveProject(Project project){
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			
			em.getTransaction().begin();
			em.persist(project);
			em.getTransaction().commit();
            return project.toString();
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
	}

    @Override
    public Project getProjectByProjectID(int projectID) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("persistence-unit");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            Project project = em.find(Project.class, projectID);
            em.getTransaction().commit();       
            return project;
        } catch(NoResultException e) {
			return null;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    @Override
    public ProjectTask getTaskByTaskID(int taskID) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("persistence-unit");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            ProjectTask task = em.find(ProjectTask.class, taskID);
            em.getTransaction().commit();           
            return task;
        } catch(NoResultException e) {
			return null;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    @Override
    public List<Project> getOngoingProjectsByDepartment(Department department){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("persistence-unit");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT p FROM project p WHERE p.department = :department AND (p.isCompleted = false AND p.isAborted = false)", Project.class);
            query.setParameter("department", department);
            List<Project> resultList = query.getResultList();
            em.getTransaction().commit();
            return resultList;
        } catch(NoResultException e) {
			return null;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    @Override
    public List<Project> getPreviousProjectsByDepartment(Department department){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("persistence-unit");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT p FROM project p WHERE p.department = :department AND (p.isCompleted = true OR p.isAborted = true)", Project.class);
            query.setParameter("department", department);
            List<Project> resultList = query.getResultList();
            em.getTransaction().commit();
            return resultList;
        } catch(NoResultException e) {
			return null;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    @Override
    public List<Project> getOngoingProjectsByStaffID(int staffID) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("persistence-unit");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            String queryStr = "FROM project p WHERE p.isCompleted = false AND p.isAborted = false AND p.staff.s_id = :staffID";
            Query query = em.createQuery(queryStr, Project.class);
            query.setParameter("staffID", staffID);
            List<Project> resultList = query.getResultList();
            em.getTransaction().commit(); 
            return resultList;
        } catch(NoResultException e) {
			return null;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    @Override
    public List<Project> getPreviousProjectsByStaffID(int staffID){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("persistence-unit");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            String queryStr = "FROM project p WHERE (p.isCompleted = true OR p.isAborted = true) AND p.staff.s_id = :staffID";
            Query query = em.createQuery(queryStr, Project.class);
            query.setParameter("staffID", staffID);
            List<Project> resultList = query.getResultList();
            em.getTransaction().commit();
            return resultList;
        } catch(NoResultException e) {
			return null;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    @Override
    public String updateProject(Project project){
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			
			em.getTransaction().begin();
			em.merge(project);
			em.getTransaction().commit();
            return project.toString();
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
	}

    @Override
    public String updateTask(ProjectTask task) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			
			em.getTransaction().begin();
			em.merge(task);
			em.getTransaction().commit();
            return task.toString();
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
	}
}
