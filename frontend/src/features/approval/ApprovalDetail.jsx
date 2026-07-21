import {
    useState
} from "react";


import {
    useParams
} from "react-router-dom";


import {
    approveApproval,
    rejectApproval
} from "./api";



function ApprovalDetail() {


    const {
        id
    } = useParams();



    const [
        comment,
        setComment
    ] = useState("");



    const [
        message,
        setMessage
    ] = useState("");






    const handleApprove = async () => {


        try {


            await approveApproval(

                id,

                comment

            );


            setMessage(
                "承認しました"
            );



        } catch (err) {


            setMessage(
                "承認に失敗しました"
            );


        }


    };







    const handleReject = async () => {


        try {


            await rejectApproval(

                id,

                comment

            );


            setMessage(
                "却下しました"
            );



        } catch (err) {


            setMessage(
                "却下に失敗しました"
            );


        }


    };







    return (

        <div>


            <h1>
                承認詳細
            </h1>




            <p>

                承認ID:
                {id}

            </p>





            {message && (

                <p>
                    {message}
                </p>

            )}






            <textarea

                placeholder="コメント"

                value={comment}

                onChange={
                    e => setComment(
                        e.target.value
                    )
                }

            />






            <br />



            <button

                onClick={handleApprove}

            >

                承認

            </button>





            <button

                onClick={handleReject}

            >

                却下

            </button>



        </div>

    );

}



export default ApprovalDetail;