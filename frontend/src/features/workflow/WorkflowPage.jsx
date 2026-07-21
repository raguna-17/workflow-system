import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import {
    getWorkflows,
    createWorkflow
} from "./api";


function WorkflowPage() {

    const [workflows, setWorkflows] = useState([]);

    const [name, setName] = useState("");

    const [error, setError] = useState("");



    const loadWorkflows = async () => {

        try {

            const data = await getWorkflows();

            setWorkflows(data);

        } catch (err) {

            setError(
                "ワークフロー取得に失敗しました"
            );

        }

    };



    useEffect(() => {

        loadWorkflows();

    }, []);




    const handleSubmit = async (e) => {

        e.preventDefault();


        try {

            await createWorkflow({
                name
            });


            setName("");

            loadWorkflows();


        } catch (err) {

            setError(
                "ワークフロー作成に失敗しました"
            );

        }

    };



    return (

        <div>

            <h1>
                ワークフロー管理
            </h1>


            {error && (
                <p>
                    {error}
                </p>
            )}



            <form onSubmit={handleSubmit}>

                <input
                    type="text"
                    placeholder="ワークフロー名"
                    value={name}
                    onChange={
                        e => setName(e.target.value)
                    }
                />


                <button type="submit">
                    作成
                </button>

            </form>



            <h2>
                一覧
            </h2>


            <ul>

                {workflows.map(workflow => (

                    <li key={workflow.id}>

                        <Link
                            to={`/workflows/${workflow.id}`}
                        >
                            {workflow.name}
                        </Link>

                    </li>

                ))}

            </ul>


        </div>

    );
}


export default WorkflowPage;