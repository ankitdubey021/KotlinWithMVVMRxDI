package com.eb.kotlinandjavaarefriends.extensions


import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
operator fun JsonObject.invoke(func: JsonObject.()->Unit){func()}
fun JsonObject.with(key:String, value:String){addProperty(key,value)}
fun JsonObject.with(key:String, value:Int){addProperty(key,value)}
fun JsonObject.with(key:String, value:Double){addProperty(key,value)}
fun JsonObject.with(key:String, value:Float){addProperty(key,value)}
fun JsonObject.with(key:String, value:Boolean){addProperty(key,value)}
fun JsonObject.with(key:String, value: JsonElement){add(key,value)}

fun giveMeJson(func: JsonObject.()->Unit): JsonObject {
    var json= JsonObject()
    json.func()
    return json
}
*/

//==json JSON===================================================================

class Json() : JSONObject(){
    constructor(json: Json.()->Unit):this(){
        json()
    }

    infix fun<T> String.to(value:T){
        put(this,value)
    }
}

//==============================================================================

class APIResponse {
    var message: String? = null
    var code: Int = 0
    var isSuccess: Boolean = false
    var data: String? = null

    override fun toString(): String {
        return "DelivrAPIResponse{" +
                "message='" + message + '\''.toString() +
                ", code=" + code +
                ", success=" + isSuccess +
                ", data='" + data + '\''.toString() +
                '}'.toString()
    }
}

fun call(call: Call<JsonElement>?, func: APIResponse.() -> Unit) {

    val apiResponse = APIResponse()

    call?.enqueue(object : Callback<JsonElement> {
        override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
            if (response.isSuccessful) {
                apiResponse.isSuccess = true
                try {
                    val OBJ = JSONObject(response.body()!!.toString())
                    apiResponse.message = OBJ.getString("message")
                    apiResponse.data = OBJ.getString("data")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            } else {
                try {
                    apiResponse.isSuccess = false
                    apiResponse.code = 101
                    val OBJ = JSONObject(response.errorBody()!!.string())
                    apiResponse.message=OBJ.getString("message")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            func(apiResponse)
        }

        override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            apiResponse.isSuccess = false

            if (t is java.net.ConnectException) {
                apiResponse.message = "Please check your Internet Connection!"

            } else
                apiResponse.message = "Something went wrong!"

            func(apiResponse)
        }
    })
}

/*inline fun <reified K:Any> getAPI()= RetrofitAPI.getInstance().create(K::class.java)*/
