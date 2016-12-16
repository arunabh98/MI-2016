package com.example.darknight.mi2016;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by darshan3 on 15/12/16.
 */

public class Faq {

    private String question;
    private String answer;

    public Faq(){

    }

    public Faq(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public Faq(JSONObject object){
        try {
            this.question = object.getString("question");
            this.answer = object.getString("answer");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // User.fromJson(jsonArray);
    public static ArrayList<Faq> fromJson(JSONArray jsonObjects) {
        ArrayList<Faq> users = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                users.add(new Faq(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return users;
    }



    public String getQuestion(){
        return question;
    }

    public String getAnswer(){
        return answer;
    }

}
