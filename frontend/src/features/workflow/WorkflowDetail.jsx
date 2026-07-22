import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import { getWorkflow, addWorkflowStep } from "./api";
import { getUsers } from "../user/api";

function WorkflowDetail() {
    const { id } = useParams();

    const [workflow, setWorkflow] = useState(null);
    const [users, setUsers] = useState([]);
    const [stepForm, setStepForm] = useState({
        stepOrder: 1,
        approverId: ""
    });
    const [error, setError] = useState("");

    const loadWorkflow = async () => {
        try {
            const data = await getWorkflow(id);
            setWorkflow(data);
        } catch (err) {
            setError("ワークフロー取得に失敗しました");
        }
    };

    useEffect(() => {
        loadWorkflow();

        const loadUsers = async () => {
            try {
                const data = await getUsers();
                setUsers(data);
            } catch (err) {
                console.error("ユーザー取得失敗");
            }
        };

        loadUsers();
    }, [id]);

    const handleAddStep = async () => {
        try {
            await addWorkflowStep(id, {
                stepOrder: Number(stepForm.stepOrder),
                approverId: Number(stepForm.approverId)
            });

            setStepForm({
                stepOrder: 1,
                approverId: ""
            });

            await loadWorkflow();
        } catch (err) {
            setError("承認ステップ追加に失敗しました");
        }
    };

    if (error) {
        return <p>{error}</p>;
    }

    if (!workflow) {
        return <p>読み込み中...</p>;
    }

    return (
        <div>
            <h1>{workflow.name}</h1>

            <p>
                作成者ID:
                {workflow.createdBy}
            </p>

            <h2>承認ステップ</h2>

            {workflow.steps.length === 0 ? (
                <p>承認ステップはありません</p>
            ) : (
                <ol>
                    {workflow.steps.map(step => (
                        <li key={step.id}>
                            {step.stepOrder} : {step.approverEmail}
                        </li>
                    ))}
                </ol>
            )}

            <h2>承認ステップ追加</h2>

            <select
                value={stepForm.approverId}
                onChange={(e) =>
                    setStepForm({
                        ...stepForm,
                        approverId: e.target.value
                    })
                }
            >
                <option value="">
                    承認者を選択
                </option>

                {users.map(user => (
                    <option key={user.id} value={user.id}>
                        {user.email}
                    </option>
                ))}
            </select>

            <input
                type="number"
                value={stepForm.stepOrder}
                onChange={(e) =>
                    setStepForm({
                        ...stepForm,
                        stepOrder: e.target.value
                    })
                }
            />

            <button onClick={handleAddStep}>
                追加
            </button>
        </div>
    );
}

export default WorkflowDetail;