import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import { getWorkflow } from "./api";


function WorkflowDetail() {

    const { id } = useParams();

    const [workflow, setWorkflow] = useState(null);

    const [error, setError] = useState("");



    useEffect(() => {

        const loadWorkflow = async () => {

            try {

                const data = await getWorkflow(id);

                setWorkflow(data);


            } catch (err) {

                setError(
                    "ワークフロー取得に失敗しました"
                );

            }

        };


        loadWorkflow();


    }, [id]);



    if (error) {

        return (
            <p>
                {error}
            </p>
        );

    }



    if (!workflow) {

        return (
            <p>
                読み込み中...
            </p>
        );

    }



    return (

        <div>


            <h1>
                {workflow.name}
            </h1>



            <p>
                作成者ID:
                {workflow.createdBy}
            </p>



            <h2>
                承認ステップ
            </h2>



            {workflow.steps.length === 0 ? (

                <p>
                    承認ステップはありません
                </p>

            ) : (

                <ol>

                    {workflow.steps.map(step => (

                        <li key={step.id}>

                            {step.stepOrder} :
                            {step.approverEmail}

                        </li>

                    ))}

                </ol>

            )}



        </div>

    );

}


export default WorkflowDetail;