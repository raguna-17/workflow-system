import {
    useEffect,
    useState
} from "react";

import {
    Link
} from "react-router-dom";


import {
    getMyRequests,
    createRequest
} from "./api";



function RequestPage() {


    const [requests, setRequests] = useState([]);


    const [title, setTitle] = useState("");

    const [content, setContent] = useState("");

    const [workflowId, setWorkflowId] = useState("");


    const [error, setError] = useState("");





    const loadRequests = async () => {


        try {


            const data = await getMyRequests();


            setRequests(data);



        } catch (err) {


            setError(
                "申請一覧取得に失敗しました"
            );


        }


    };






    useEffect(() => {


        loadRequests();


    }, []);








    const handleSubmit = async (e) => {


        e.preventDefault();



        try {


            await createRequest({

                title,

                content,

                workflowId: Number(workflowId)

            });




            setTitle("");

            setContent("");

            setWorkflowId("");



            loadRequests();



        } catch (err) {


            setError(
                "申請作成に失敗しました"
            );


        }


    };







    return (

        <div>



            <h1>
                申請管理
            </h1>





            {error && (

                <p>
                    {error}
                </p>

            )}






            <form onSubmit={handleSubmit}>


                <h2>
                    申請作成
                </h2>





                <input

                    type="text"

                    placeholder="タイトル"

                    value={title}

                    onChange={
                        e => setTitle(
                            e.target.value
                        )
                    }

                />





                <textarea

                    placeholder="内容"

                    value={content}

                    onChange={
                        e => setContent(
                            e.target.value
                        )
                    }

                />





                <input

                    type="number"

                    placeholder="ワークフローID"

                    value={workflowId}

                    onChange={
                        e => setWorkflowId(
                            e.target.value
                        )
                    }

                />





                <button type="submit">

                    申請する

                </button>



            </form>







            <h2>
                自分の申請一覧
            </h2>





            <ul>


                {
                    requests.map(request => (


                        <li
                            key={request.id}
                        >


                            <Link

                                to={`/requests/${request.id}`}

                            >

                                {request.title}

                            </Link>



                            {" "}


                            状態:

                            {request.status}



                        </li>


                    ))
                }



            </ul>





        </div>

    );

}



export default RequestPage;