package apis;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import utils.ApiActions;

import java.util.HashMap;
import java.util.Map;

public class JsonPlaceHolderApi {
    ApiActions apiActions;
    String usersResource = "/users";
    String postsResource = "/posts";


    public JsonPlaceHolderApi(ApiActions apiActions) {
        this.apiActions = apiActions;
    }

    public Response getUserByID(String id){

        return apiActions.sendRequest(ApiActions.RequestType.GET, usersResource+"/"+id, null, null, null, null, null);

    }

    public Response getPostsByUserID(String id){

        Map<String,Object> queryParams = new HashMap<>() ;
        queryParams.put("userId",id);
        return apiActions.sendRequest(ApiActions.RequestType.GET, postsResource, null, null, queryParams, null, null);

    }

    public Response createPostForUser(String title , String body,String userId){
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", ContentType.JSON);

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("userId", userId);
        bodyMap.put("title", title);
        bodyMap.put("body", body);
        JSONObject bodyObj = new JSONObject(bodyMap);

        return apiActions.sendRequest(ApiActions.RequestType.POST, postsResource, headers, null, null, bodyObj, null);

    }




}
