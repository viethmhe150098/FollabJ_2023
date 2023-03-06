import React from 'react';
import { useParams } from 'react-router';

const TestParams = () => {

    let { project_id, task_id } = useParams();

    return ( 
        <h1>
            Project ID is {project_id} and Task ID is {task_id}
        </h1>
    );
}

export default TestParams;