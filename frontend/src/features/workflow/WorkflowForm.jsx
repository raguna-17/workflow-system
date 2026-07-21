import { useState } from "react";

import { createWorkflow } from "./api";


function WorkflowForm({ onSuccess }) {

    const [name, setName] = useState("");
    const [error, setError] = useState("");


    const handleSubmit = async (e) => {

        e.preventDefault();


        try {

            await createWorkflow({
                name
            });


            setName("");


            if (onSuccess) {
                onSuccess();
            }


        } catch (err) {

            setError(
                "ワークフロー作成に失敗しました"
            );

        }

    };


    return (

        <form onSubmit={handleSubmit}>


            <h2>
                ワークフロー作成
            </h2>


            {error && (
                <p>
                    {error}
                </p>
            )}



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

    );

}


export default WorkflowForm;