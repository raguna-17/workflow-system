import {
    useEffect,
    useState
} from "react";


import {
    getDashboard
} from "./api";



function DashboardPage() {


    const [dashboard, setDashboard] = useState(null);


    const [error, setError] = useState("");





    const loadDashboard = async () => {


        try {


            const data = await getDashboard();


            setDashboard(data);



        } catch (err) {


            setError(
                "ダッシュボード取得に失敗しました"
            );


        }


    };






    useEffect(() => {


        loadDashboard();


    }, []);







    if (error) {

        return (

            <p>
                {error}
            </p>

        );

    }





    if (!dashboard) {

        return (

            <p>
                読み込み中...
            </p>

        );

    }







    return (

        <div>


            <h1>
                ダッシュボード
            </h1>





            <div>


                <h2>
                    申請状況
                </h2>


                <p>

                    作成申請数:

                    {dashboard.requestCount}

                </p>




                <p>

                    承認済み:

                    {dashboard.approvedCount}

                </p>




                <p>

                    却下:

                    {dashboard.rejectedCount}

                </p>



            </div>







            <div>


                <h2>
                    承認待ち
                </h2>



                <p>

                    承認待ち件数:

                    {dashboard.pendingApprovalCount}

                </p>



            </div>




        </div>

    );

}



export default DashboardPage;