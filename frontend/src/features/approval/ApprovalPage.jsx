import {
    useState
} from "react";

import {
    Link
} from "react-router-dom";


import {
    getApprovals
} from "./api";



function ApprovalPage() {


    const [requestId, setRequestId] = useState("");

    const [approvals, setApprovals] = useState([]);

    const [error, setError] = useState("");






    const handleSearch = async (e) => {

        e.preventDefault();


        try {


            const data = await getApprovals(
                requestId
            );


            setApprovals(data);



        } catch (err) {


            setError(
                "承認一覧取得に失敗しました"
            );


        }


    };







    return (

        <div>


            <h1>
                承認管理
            </h1>




            {error && (

                <p>
                    {error}
                </p>

            )}






            <form onSubmit={handleSearch}>


                <input

                    type="number"

                    placeholder="申請ID"

                    value={requestId}

                    onChange={
                        e => setRequestId(
                            e.target.value
                        )
                    }

                />



                <button type="submit">

                    検索

                </button>



            </form>







            <h2>
                承認一覧
            </h2>






            <ul>


                {
                    approvals.map(approval => (


                        <li
                            key={approval.id}
                        >


                            <Link

                                to={`/approvals/${approval.id}`}

                            >

                                承認ID:
                                {approval.id}

                            </Link>



                            {" "}

                            状態:

                            {approval.status}



                        </li>


                    ))
                }


            </ul>



        </div>

    );

}



export default ApprovalPage;