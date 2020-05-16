package app.flow.data.network.api

object ApiConstant {

    private const val BASE_URI = "https://flowappapi.azurewebsites.net"
    private const val BASE_PORT = "443"
    private const val BASE_API_VERSION = "api"

    const val BASE_HOST: String = "${BASE_URI}:${BASE_PORT}/"

    const val AUTH_LOGIN_URI: String = "${BASE_API_VERSION}/auth/login"
    const val AUTH_REGISTER_URI: String = "${BASE_API_VERSION}/auth/register"

    const val USERS_URI: String = "${BASE_API_VERSION}/user"
    const val USERS_PICTURE_URI: String = "${BASE_API_VERSION}/user/changePicture"

    const val POSTS_URI: String = "${BASE_API_VERSION}/posts"
    const val POSTS_PAGE_URI: String = "${BASE_API_VERSION}/posts/{page}"
    const val POSTS_GET_URI: String = "${BASE_API_VERSION}/posts/post/{postId}"

    const val RATE_URI: String = "${BASE_API_VERSION}/rates"
    const val RATE_GET_URI: String = "${BASE_API_VERSION}/rates/{postId}"
}