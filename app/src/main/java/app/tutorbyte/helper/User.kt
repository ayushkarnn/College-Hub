package app.tutorbyte.helper

data class User(
    val email:String,
    val signupDt:String,
    val lastLogin:String,
    val lastPasswordReset:String,
    val loginCnt:String,
    val isProfileCompleted:Boolean

//    val _androidId:String,
//    val location:String
)