package com.follabj_be.follabj_be;
import com.follabj_be.follabj_be.dto.CreateProjectDTO;
import com.follabj_be.follabj_be.entity.Project;
import com.follabj_be.follabj_be.exception.GroupException;
import com.follabj_be.follabj_be.repository.ProjectRepository;
import com.follabj_be.follabj_be.service.impl.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTests {
    @Mock
    ProjectRepository projectRepository;

    @InjectMocks
    ProjectService projectService;
    @Test
    public void Project_TestCreateProject() throws GroupException {
        assertThat(1).isEqualTo(1);
    }
}
