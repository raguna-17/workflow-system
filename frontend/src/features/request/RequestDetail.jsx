import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getRequestDetail } from "./api";

function RequestDetail() {
    const { id } = useParams();
    const [request, setRequest] = useState(null);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadRequest = async () => {
            try {
                const data = await getRequestDetail(id);
                setRequest(data);
            } catch (err) {
                setError("申請取得に失敗しました");
            }
        };

        loadRequest();
    }, [id]);

    if (error) {
        return <p>{error}</p>;
    }

    if (!request) {
        return <p>読み込み中...</p>;
    }

    return (
        <div>
            <h1>申請詳細</h1>

            <p>ID: {request.id}</p>

            <p>タイトル: {request.title}</p>

            <p>内容: {request.content}</p>

            <p>状態: {request.status}</p>

            <p>ワークフローID: {request.workflowId}</p>

            <p>作成日時: {request.createdAt}</p>
        </div>
    );
}

export default RequestDetail;