import api from "../../lib/axios";


export const getWorkflows = async () => {

    const response = await api.get(
        "/workflows"
    );

    return response.data;
};


export const createWorkflow = async (data) => {

    const response = await api.post(
        "/workflows",
        data
    );

    return response.data;
};


export const getWorkflow = async (id) => {

    const response = await api.get(
        `/workflows/${id}`
    );

    return response.data;
};

export const addWorkflowStep = async (workflowId, data) => {

    await api.post(
        `/workflows/${workflowId}/steps`,
        data
    );

};