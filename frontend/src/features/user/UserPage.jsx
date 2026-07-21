import UserForm from "./UserForm";


function UserPage() {


    const handleSuccess = () => {

        alert(
            "ユーザーを登録しました"
        );

    };


    return (

        <div>

            <h1>
                ユーザー管理
            </h1>


            <UserForm
                onSuccess={handleSuccess}
            />


        </div>

    );

}


export default UserPage;